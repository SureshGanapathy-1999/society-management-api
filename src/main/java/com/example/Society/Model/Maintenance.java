package com.example.Society.Model;

import com.example.Society.Config.MaintenanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "maintenance_bill",
        uniqueConstraints = {@UniqueConstraint( columnNames = {"flat_id","bill_month","bill_year"}
        )
    }
)
public class Maintenance extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Maintenance bill details
    @Column(name = "bill_number", nullable = false,unique = true)
    private String billNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flat_id",nullable = false)
    private Flat flat;

    @Column(name = "bill_month")
    private Integer billMonth;
    @Column(name = "bill_year")
    private Integer billYear;

    @Column(nullable = false)
    private BigDecimal maintenanceCost;

    @Column(nullable = false)
    private BigDecimal lateFee =  BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceStatus maintenanceStatus;
    private LocalDateTime generatedAt;

    public Maintenance() {
        this.lateFee = BigDecimal.ZERO;
        this.maintenanceStatus = MaintenanceStatus.GENERATED;
    }

}
