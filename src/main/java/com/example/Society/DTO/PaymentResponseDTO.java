package com.example.Society.DTO;

import com.example.Society.Config.PaymentMode;
import com.example.Society.Config.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentResponseDTO {

    private Long id;
    private Long maintenanceId;
    private String billNumber;
    private Long flatId;
    private Integer billMonth;
    private Integer billYear;

    private BigDecimal amountPaid;
    private LocalDateTime paymentDate;

    private PaymentStatus paymentStatus;
    private PaymentMode paymentMode;

    private String transactionRef;
    private String remarks;
    private String receivedBy;
    private LocalDateTime createdAt;
}
