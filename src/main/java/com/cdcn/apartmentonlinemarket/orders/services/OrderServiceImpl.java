package com.cdcn.apartmentonlinemarket.orders.services;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import com.cdcn.apartmentonlinemarket.common.enums.PaymentMethod;
import com.cdcn.apartmentonlinemarket.common.enums.PaymentStatus;
import com.cdcn.apartmentonlinemarket.exception.ProductNotEnoughException;
import com.cdcn.apartmentonlinemarket.exception.UserNotFoundException;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.CreateOrderResponse;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.OrderItemDto;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.request.CreateOrderRequest;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.OrderItem;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.Orders;
import com.cdcn.apartmentonlinemarket.orders.domain.mapper.OrderItemMapper;
import com.cdcn.apartmentonlinemarket.orders.model.*;
import com.cdcn.apartmentonlinemarket.orders.repository.OrderRepository;
import com.cdcn.apartmentonlinemarket.orders.repository.PaymentRepository;
import com.cdcn.apartmentonlinemarket.payments.domain.entity.Payment;
import com.cdcn.apartmentonlinemarket.products.services.InventoryService;
import com.cdcn.apartmentonlinemarket.security.jwt.TokenProvider;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import com.cdcn.apartmentonlinemarket.users.repository.UserRepository;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.json.JSONDeserializer;
import nonapi.io.github.classgraph.json.JSONSerializer;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemService orderItemService;
    private final InventoryService inventoryService;

    @Override
    public Response checkout(String order_reference, String return_url) throws UnsupportedEncodingException {
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
        vnp_Params.put("vnp_ReturnUrl", return_url);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        cld.add(Calendar.HOUR, 7);
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

    @Override
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        Orders orders = new Orders();
        orders.setOrderStatus(OrderStatus.CREATED);
        orders.setReference(Config.generateReference(10));
        String userId = tokenProvider.getUserId();
        Users users = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() ->
                new UserNotFoundException("User not found!"));
        orders.setUser(users);
//        orders.setExpiredAt(Timestamp.from( Instant.now().plusSeconds(3600)));
        Orders savedOrder = save(orders);

        List<OrderItem> orderItemList = orderItemMapper.convertToListEntity(request);
        orderItemList.forEach(orderItem -> {
            inventoryService.descreaseQuantityStock(orderItem.getProductId(), orderItem.getQuantity());
            orderItem.setOrderId(savedOrder.getId());
        });
        List<OrderItemDto> savedOrderItemDto = orderItemService.saveAll(orderItemList);
//        scheduleCancelOrderExpired();
        return new CreateOrderResponse(savedOrder.getId(), savedOrder.getReference(),
                totalAmountOrder(orderItemList), savedOrder.getOrderStatus(),
                savedOrderItemDto);
    }

    @Override
    public Response orderHistories(UUID user_id) {
        Response res = new Response();
        try
        {
            List<OrderHistoryResponse> data = orderRepository.orderHistories(user_id);
            if(data != null)
            {
                res.setMessage("success");
                res.setRspCode("200");
                res.setData(data);
            }
            else {
                res.setMessage("notfound");
                res.setRspCode("403");
            }
        }
        catch (Exception e)
        {
            res.setMessage("Error: "+ e.toString());
            res.setRspCode("500");
        }
        return res;
    }

    @Override
    public Response orderDetail(UUID order_id) {
        Response res = new Response();
        try
        {
            OrderDetailResponse data = orderRepository.orderDetail(order_id);
            if(data != null)
            {
                data.setItems(orderRepository.orderItems(order_id));
                res.setMessage("success");
                res.setRspCode("200");
                res.setData(data);
            }
            else {
                res.setMessage("notfound");
                res.setRspCode("403");
            }
        }
        catch (Exception e)
        {
            res.setMessage("Error: "+ e.toString());
            res.setRspCode("500");
        }
        return res;
    }

    @Override
    public Response storeOrderDetail(UUID order_id, UUID store_id) {
        Response res = new Response();
        try
        {
            OrderDetailResponse data = orderRepository.orderDetail(order_id);
            if(data != null)
            {
                data.setItems(orderRepository.storeOrderItems(order_id, store_id));
                res.setMessage("success");
                res.setRspCode("200");
                res.setData(data);
            }
            else {
                res.setMessage("notfound");
                res.setRspCode("403");
            }
        }
        catch (Exception e)
        {
            res.setMessage("Error: "+ e.toString());
            res.setRspCode("500");
        }
        return res;
    }

    @Override
    public Response storeOrderHistories(UUID store_id) {
        Response res = new Response();
        try
        {
            List<OrderHistoryResponse> data = orderRepository.storeOrderHistories(store_id);
            if(data != null)
            {
                res.setMessage("success");
                res.setRspCode("200");
                res.setData(data);
            }
            else {
                res.setMessage("notfound");
                res.setRspCode("403");
            }
        }
        catch (Exception e)
        {
            res.setMessage("Error: "+ e.toString());
            res.setRspCode("500");
        }
        return res;
    }

    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    private BigDecimal totalAmountOrder(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(orderItem -> orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

//    public void scheduleCancelOrderExpired() {
//        StopWatch stopWatch = StopWatch.createStarted();
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                stopWatch.stop();
//                orderRepository.cancelOrderExpired(OrderStatus.CANCELED, Timestamp.from(Instant.now()));
//                executor.shutdown();
//            }
//        }, 60, TimeUnit.MINUTES);
//
//    }
}
