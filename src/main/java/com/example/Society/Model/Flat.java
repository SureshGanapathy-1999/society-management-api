package com.example.Society.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

@Entity
@Table(name = "flat",
uniqueConstraints = {@UniqueConstraint(columnNames = {"wing_id","flat_number"})})
public class Flat extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flat_number", nullable = false)
    private String flatNumber;
    private BigDecimal maintenanceCost;
    private BigDecimal flatSqft;

    // Relationships
    // One wing can have multiple flats
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wing_id", nullable = false)
    private Wing wing;
}
