package com.example.Society.Model;

import com.example.Society.Config.PaymentMode;
import com.example.Society.Config.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Payment extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id",nullable = false)
    private Maintenance maintenance;

    private BigDecimal amountPaid;
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMode paymentMode;

    private String transactionRef;
    private String remarks;
    private String receivedBy;

    public void prePersist() {
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
    }
}
