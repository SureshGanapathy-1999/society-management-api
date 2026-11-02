package com.example.Society.DTO;


import com.example.Society.Config.PaymentMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequestDTO {

    @NotNull
    private Long maintenanceId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amountPaid;

    @NotNull
    private PaymentMode paymentMode;

    @Size(max = 100)
    private String transactionRef;

    @Size(max = 500)
    private String remarks;
}
