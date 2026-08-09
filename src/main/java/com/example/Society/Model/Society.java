package com.example.Society.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "society")
public class Society extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String societyCode;

    // Basic society details
    @Column(nullable = false)
    private String societyName;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    // Relationships

    // One Society contains multiple Wings
    @OneToMany(mappedBy = "society", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Wing> wings;

   }
