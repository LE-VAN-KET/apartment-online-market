package com.cdcn.apartmentonlinemarket.payments.domain.entity;


import com.cdcn.apartmentonlinemarket.common.enums.PaymentMethod;
import com.cdcn.apartmentonlinemarket.common.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod type;

    @CreditCardNumber(ignoreNonDigitCharacters = true)
    private String cardNumber;

    private UUID orderId;
    private BigDecimal totalAmount;
    @Column(length = 10000)
    private String details;
    private String reference;
    private String currentCode;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Transaction transaction;

    private OffsetDateTime createdDate;

}
