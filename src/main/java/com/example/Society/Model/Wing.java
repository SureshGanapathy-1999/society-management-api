package com.example.Society.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "wing")
public class Wing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String wing_name;

    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWing_name() {
        return wing_name;
    }

    public void setWing_name(String wing_name) {
        this.wing_name = wing_name;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }
}
