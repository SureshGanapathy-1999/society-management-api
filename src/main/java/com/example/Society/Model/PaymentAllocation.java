package com.example.Society.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(
        name = "payment_allocation",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"payment_id", "maintenance_id"}
                )
        }
)
public class PaymentAllocation extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amountAllocated;

}
