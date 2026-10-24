package com.example.Society.DTO;

import com.example.Society.Config.MaintenanceStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MaintenanceResponseDTO {
    private Long id;
    private String billNumber;
    private Long flatId;
    private Integer billMonth;
    private Integer billYear;
    private BigDecimal maintenanceCost;
    private BigDecimal lateFee;
    private BigDecimal totalAmount;
    private LocalDate dueDate;
    private MaintenanceStatus maintenanceStatus;
    private LocalDateTime generatedAt;
}
