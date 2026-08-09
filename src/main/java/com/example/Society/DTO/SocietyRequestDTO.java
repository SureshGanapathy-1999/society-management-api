package com.example.Society.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocietyRequestDTO {


    @NotBlank
    private String societyCode;
    @NotBlank
    private String societyName;
    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String pinCode;


}
