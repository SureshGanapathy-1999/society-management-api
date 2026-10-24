package com.example.Society.Repository;

import com.example.Society.Model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    boolean existsByFlatIdAndBillNumberAndBillYear(Long flatId,Integer billNumber,Integer billYear);
}
