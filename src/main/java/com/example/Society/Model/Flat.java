package com.example.Society.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "flat",
uniqueConstraints = {@UniqueConstraint(columnNames = {"wing_id","flat_number"})})
public class Flat extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flat_number")
    private String flatNumber;
    private double maintenanceCost;
    private double flatSqft;

    // Relationships

    // One wing can have multiple flats
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wing_id")
    private Wing wing;

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public double getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(double maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public double getFlatSqft() {
        return flatSqft;
    }

    public void setFlatSqft(double flatSqft) {
        this.flatSqft = flatSqft;
    }

    public Wing getWing() {
        return wing;
    }

    public void setWing(Wing wing) {
        this.wing = wing;
    }
}
