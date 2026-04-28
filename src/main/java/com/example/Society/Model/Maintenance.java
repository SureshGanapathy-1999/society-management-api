package com.example.Society.Model;

import com.example.Society.Config.MaintenanceStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private double maintenanceCost;
    private double lateFee;
    private double totalAmount;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceStatus maintenanceStatus;
    private LocalDateTime generatedAt;

    @PrePersist
    public void prePersist() {
        if (maintenanceStatus == null) {
            maintenanceStatus = MaintenanceStatus.GENERATED;
        }
    }


    //Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public Integer getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(Integer billMonth) {
        this.billMonth = billMonth;
    }

    public Integer getBillYear() {
        return billYear;
    }

    public void setBillYear(Integer billYear) {
        this.billYear = billYear;
    }

    public double getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(double maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public double getLateFee() {
        return lateFee;
    }

    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public MaintenanceStatus getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(MaintenanceStatus maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}
