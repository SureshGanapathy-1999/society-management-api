package com.example.Society.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class FlatRequestDTO {

    @NotBlank
    private String flatNumber;

    @NotNull(message = "Flat Sq.Ft is required")
    @Positive(message = "Flat Sq.Ft must be greater than zero")
    private BigDecimal flatSqft;

    private Long ownerId;

}
