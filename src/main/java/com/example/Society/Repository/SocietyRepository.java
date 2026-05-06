package com.example.Society.Repository;

import com.example.Society.Model.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocietyRepository extends  JpaRepository<Society, Long> {
}
