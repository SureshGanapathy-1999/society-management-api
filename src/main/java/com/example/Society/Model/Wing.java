package com.example.Society.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "wing")
public class Wing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Wing Name
    private String wingName;

    // RelationShips

    // one society contains many wings
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id")
    private Society society;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWingName() {
        return wingName;
    }

    public void setWingName(String wingName) {
        this.wingName = wingName;
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }
}
