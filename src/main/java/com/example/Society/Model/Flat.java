package com.example.Society.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Flat Sq.Ft is required")
    private BigDecimal flatSqft;

    // Relationships
    // One wing can have multiple flats
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wing_id", nullable = false)
    private Wing wing;

    // One member can own multiple flats
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member owner;
}
