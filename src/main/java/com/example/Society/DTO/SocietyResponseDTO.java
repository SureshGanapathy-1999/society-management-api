package com.example.Society.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SocietyResponseDTO {

    private Long id;
    private String societyCode;
    private String societyName;
    private String address;
    private String city;
    private String state;
    private String pinCode;
}
