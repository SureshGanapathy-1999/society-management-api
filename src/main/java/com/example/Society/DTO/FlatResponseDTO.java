package com.example.Society.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class FlatResponseDTO {

    private Long id;
    private String flatNumber;
    private BigDecimal maintenanceCost;
    private BigDecimal flatSqft;
    private Long wingId;
    private String wingName;
}
