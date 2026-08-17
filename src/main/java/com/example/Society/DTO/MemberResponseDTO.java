package com.example.Society.DTO;

import com.example.Society.Config.MemberStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private MemberStatus memberStatus;
}
