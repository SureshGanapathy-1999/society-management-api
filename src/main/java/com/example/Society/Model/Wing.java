package com.example.Society.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wing",
uniqueConstraints = {@UniqueConstraint(columnNames = {"society_id","wing_name"})})
public class Wing extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Wing Name
    @Column(name = "wing_name")
    private String wingName;

    // RelationShips

    // one society contains many wings
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id")
    @JsonBackReference
    private Society society;

    //One wing can contain multiple flats
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Flat>  flats;

    //Getters and Setters

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
