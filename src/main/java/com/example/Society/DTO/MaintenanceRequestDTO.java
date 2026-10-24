package com.example.Society.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MaintenanceRequestDTO {

    @NotNull
    private Long flatId;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer billMonth;

    @NotNull
    private Integer billYear;

    private LocalDate dueDate;

}
