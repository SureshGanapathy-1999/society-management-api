package com.example.Society.Repository;

import com.example.Society.Config.MaintenanceStatus;
import com.example.Society.Model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    boolean existsByFlatIdAndBillMonthAndBillYear(Long flatId,Integer billMonth,Integer billYear);

    List<Maintenance> findByMaintenanceStatusInAndDueDateBefore(
            List<MaintenanceStatus> statuses,
            LocalDate currentDate
    );
}
