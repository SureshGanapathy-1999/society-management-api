package com.example.Society.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "wing",
uniqueConstraints = {@UniqueConstraint(columnNames = {"society_id","wing_name"})})
public class Wing extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

}
