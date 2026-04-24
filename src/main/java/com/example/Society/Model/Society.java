package com.example.Society.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "society")
public class Society {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic society details
    private String societyName;
    private String address;
    private String city;
    private String state;
    private int pinCode;

    // Relationships

    // One Society contains multiple Wings
    @OneToMany(mappedBy = "society", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Wing> wings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public List<Wing> getWings() {
        return wings;
    }

    public void setWings(List<Wing> wings) {
        this.wings = wings;
    }



   }
