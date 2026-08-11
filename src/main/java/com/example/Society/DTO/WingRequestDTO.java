package com.example.Society.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class WingRequestDTO {

    @NotBlank
    private String wingName;
}
