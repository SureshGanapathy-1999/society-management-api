package com.example.Society.Model;

import com.example.Society.Config.MemberStatus;
import jakarta.persistence.*;
import java.util.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table (name = "member")
public class Member extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Basic details
    @NotBlank(message = "First Name is Required")
    private String firstName;
    private String lastName;

    @NotBlank(message = "Mobile number is required")
    @Column(unique = true, nullable = false)
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid mobile number"
    )
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus =  MemberStatus.ACTIVE;


    //Relationships

    // One member can own multiple flats
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Flat> flats;

}
