package com.example.Society.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceBulkRequestDTO {

    @NotNull
    @Min(1)
    @Max(12)
    private Integer billMonth;

    @NotNull
    @Min(2020)
    @Max(2100)
    private Integer billYear;
}
