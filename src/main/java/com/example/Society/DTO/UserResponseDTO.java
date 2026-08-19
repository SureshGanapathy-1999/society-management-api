package com.example.Society.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private Boolean active;
    private Boolean accountLocked;
    private Boolean credentialsExpired;
}
