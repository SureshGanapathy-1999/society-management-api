package com.example.Society.Repository;

import com.example.Society.Model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface FlatRepository extends JpaRepository<Flat,Long> {

    List<Flat> findByWingId(Long wingId);

    boolean existsByWingIdAndFlatNumber(Long wingId, String flatNumber);

    boolean existsByOwnerId(Long ownerId);
}
