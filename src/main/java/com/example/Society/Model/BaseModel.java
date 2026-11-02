package com.example.Society.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@MappedSuperclass
public abstract class BaseModel {

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    //Automatically runs before insert
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = "admin";
        this.updatedBy = "admin";
    }

    //Automatically runs before Update
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "admin";
    }
}
