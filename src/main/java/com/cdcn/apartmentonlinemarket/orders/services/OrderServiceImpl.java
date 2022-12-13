package com.cdcn.apartmentonlinemarket.orders.services;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import com.cdcn.apartmentonlinemarket.common.enums.PaymentMethod;
import com.cdcn.apartmentonlinemarket.common.enums.PaymentStatus;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.Orders;
import com.cdcn.apartmentonlinemarket.orders.model.IPNRequest;
import com.cdcn.apartmentonlinemarket.orders.model.OrderResponse;
import com.cdcn.apartmentonlinemarket.orders.model.Response;
import com.cdcn.apartmentonlinemarket.orders.repository.OrderRepository;
import com.cdcn.apartmentonlinemarket.orders.repository.PaymentRepository;
import com.cdcn.apartmentonlinemarket.payments.domain.entity.Payment;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.json.JSONDeserializer;
import nonapi.io.github.classgraph.json.JSONSerializer;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    @Override
    public Response checkout(String order_reference) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = order_reference;
        String vnp_TxnRef = order_reference;
        String vnp_IpAddr =  "123.19.179.109"; //Config.getIpAddress(req);
        String vnp_TmnCode = Config.vnp_TmnCode;
        OrderResponse order = orderRepository.findByReference(order_reference);
        Response job = new Response();

        if(order.getId() == null)
        {
            job.setRspCode("01");
            job.setMessage("order not found");
            return job;
        }
//        OrderResponse order = exist.get();
        BigDecimal a = order.getTotalAmount().multiply(new BigDecimal(100));
        String am = String.valueOf(a.longValue());
        Map vnp_Params = new HashMap();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", am);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);

        String locate = "vn"; // just support vn/en
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Build data to hash and querystring
        List fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        job.setRspCode("00");
        job.setMessage("success");
        job.setData(paymentUrl);
        return job;
    }

    @Override
    public Response IPN(IPNRequest data) throws UnsupportedEncodingException {
        Response job = new Response();
        try
        {
            OrderResponse order = orderRepository.findByReference(data.getVnp_TxnRef());
            if(order.getId() == null)
            {
                job.setRspCode("01");
                job.setMessage("Order not Found");
                return job;
            }
//            OrderResponse order = exist.get();
            Optional<Orders> dbOrderCheck = orderRepository.findById(order.getId());
            if (!dbOrderCheck.isPresent())
            {
                job.setRspCode("01");
                job.setMessage("Order not Found");
                return job;
            }
            Orders dbOrder = dbOrderCheck.get();
            //Begin process return from VNPAY
            Map fields = new HashMap();
            fields.put("vnp_TmnCode", data.getVnp_TmnCode());
            fields.put("vnp_Amount", data.getVnp_Amount());
            fields.put("vnp_BankCode", data.getVnp_BankCode());
            fields.put("vnp_BankTranNo", data.getVnp_BankTranNo());
            fields.put("vnp_CardType", data.getVnp_CardType());
            fields.put("vnp_PayDate", data.getVnp_PayDate());
            fields.put("vnp_OrderInfo", data.getVnp_OrderInfo());
            fields.put("vnp_TransactionNo", data.getVnp_TransactionNo());
            fields.put("vnp_ResponseCode", data.getVnp_ResponseCode());
            fields.put("vnp_TransactionStatus", data.getVnp_TransactionStatus());
            fields.put("vnp_TxnRef", data.getVnp_TxnRef());
            fields.put("vnp_SecureHashType", data.getVnp_SecureHashType());
            fields.put("vnp_SecureHash", data.getVnp_SecureHash());

            String vnp_SecureHash = data.getVnp_SecureHash();
            if (fields.containsKey("vnp_SecureHashType"))
            {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash"))
            {
                fields.remove("vnp_SecureHash");
            }

            // Check checksum
            String signValue = Config.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash))
            {
                boolean checkAmount = data.getVnp_Amount().equals(String.valueOf(order.getTotalAmount().multiply(new BigDecimal(100)).longValue()));
                boolean checkOrderStatus = order.getOrderStatus().getValue() == 0;
                if(checkAmount)
                {
                    if (checkOrderStatus)
                    {
                        Payment payment = new Payment();
                        payment.setType(PaymentMethod.CREDIT_CARDS);
                        payment.setOrderId(order.getId());
                        payment.setTotalAmount(order.getTotalAmount());
                        payment.setDetails(JSONSerializer.serializeObject(data));
                        payment.setReference(Config.generateReference(15));
                        payment.setCurrentCode("VND");
                        payment.setCreatedDate(OffsetDateTime.now());
                        if ("00".equals(data.getVnp_ResponseCode()))
                        {
                            dbOrder.setOrderStatus(OrderStatus.COMPLETED);
                            payment.setStatus(PaymentStatus.SUCCESS);
                        } else if (data.getVnp_ResponseCode().equals("24")){
                            dbOrder.setOrderStatus(OrderStatus.CANCELED);
                            payment.setStatus(PaymentStatus.FAILED);
                        }
                        else
                        {
                            dbOrder.setOrderStatus(OrderStatus.FAILED);
                            payment.setStatus(PaymentStatus.FAILED);
                        }
                        orderRepository.save(dbOrder);
                        paymentRepository.save(payment);
                        job.setRspCode("00");
                        job.setMessage("Confirm Success");
                        return job;
                    }
                    else
                    {
                        job.setRspCode("02");
                        job.setMessage("Order already confirmed");
                        return job;
                    }
                }
                else
                {
                    job.setRspCode("04");
                    job.setMessage("Invalid Amount");
                    return job;
                }
            }
            else
            {
                job.setRspCode("97");
                job.setMessage("Invalid Checksum");
                return job;
            }
        }
        catch(Exception e)
        {
            job.setRspCode("99");
            job.setMessage("Unknow error");
            return job;
        }
    }
    public Response Success(IPNRequest data) throws UnsupportedEncodingException {
        Response job = new Response();
        try {
            OrderResponse order = orderRepository.findByReference(data.getVnp_TxnRef());
            if (order.getId() == null) {
                job.setRspCode("01");
                job.setMessage("Order not Found");
                return job;
            }
//            OrderResponse order = exist.get();
            Optional<Orders> dbOrderCheck = orderRepository.findById(order.getId());
            if (!dbOrderCheck.isPresent()) {
                job.setRspCode("01");
                job.setMessage("Order not Found");
                return job;
            }
            Orders dbOrder = dbOrderCheck.get();
            //Begin process return from VNPAY
            Map fields = new HashMap();
            fields.put("vnp_TmnCode", data.getVnp_TmnCode());
            fields.put("vnp_Amount", data.getVnp_Amount());
            fields.put("vnp_BankCode", data.getVnp_BankCode());
            fields.put("vnp_BankTranNo", data.getVnp_BankTranNo());
            fields.put("vnp_CardType", data.getVnp_CardType());
            fields.put("vnp_PayDate", data.getVnp_PayDate());
            fields.put("vnp_OrderInfo", data.getVnp_OrderInfo());
            fields.put("vnp_TransactionNo", data.getVnp_TransactionNo());
            fields.put("vnp_ResponseCode", data.getVnp_ResponseCode());
            fields.put("vnp_TransactionStatus", data.getVnp_TransactionStatus());
            fields.put("vnp_TxnRef", data.getVnp_TxnRef());
            fields.put("vnp_SecureHashType", data.getVnp_SecureHashType());
            fields.put("vnp_SecureHash", data.getVnp_SecureHash());

            String vnp_SecureHash = data.getVnp_SecureHash();
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }

            // Check checksum
            String signValue = Config.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {
                boolean checkAmount = data.getVnp_Amount().equals(String.valueOf(order.getTotalAmount().multiply(new BigDecimal(100)).longValue()));
                boolean checkOrderStatus = order.getOrderStatus().getValue() == 0;

                if (checkAmount) {
                    if (checkOrderStatus) {
                    if ("00".equals(data.getVnp_ResponseCode())) {
                        job.setRspCode("00");
                        job.setMessage("Confirm Success");
                        return job;
                    } else if (data.getVnp_ResponseCode().equals("24")) {
                        job.setRspCode("24");
                        job.setMessage("Cancel Order");
                        return job;
                    } else {
                        job.setRspCode("01");
                        job.setMessage("Payment fail");
                        return job;
                    }
                    } else {
                        job.setRspCode("02");
                        job.setMessage("Order already confirmed");
                        return job;
                    }
                } else {
                    job.setRspCode("04");
                    job.setMessage("Invalid Amount");
                    return job;
                }
            } else {
                job.setRspCode("97");
                job.setMessage("Invalid Checksum");
                return job;
            }
        } catch (Exception e) {
            job.setRspCode("99");
            job.setMessage("Unknow error");
            return job;
        }
    }
}
