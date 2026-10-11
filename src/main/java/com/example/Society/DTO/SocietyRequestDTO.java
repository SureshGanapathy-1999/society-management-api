package com.example.Society.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(
            regexp = "^[1-9][0-9]{5}$",
            message = "Invalid PIN code"
    )
    @NotBlank
    private String pinCode;
}
