package com.example.Society.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class FlatRequestDTO {

    @NotBlank
    private String flatNumber;

    @PositiveOrZero
    private BigDecimal maintenanceCost;

    @Positive
    private BigDecimal flatSqft;

}
