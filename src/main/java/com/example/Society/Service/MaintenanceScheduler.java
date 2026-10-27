package com.example.Society.Service;

import com.example.Society.DTO.MaintenanceBulkRequestDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class MaintenanceScheduler {

    private final MaintenanceService maintenanceService;

    public MaintenanceScheduler(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @Scheduled(
            cron = "0 0 0 1 * *",
            zone = "Asia/Kolkata"
    )

    public void generateMonthlyMaintenanceBills() {

        LocalDate today = LocalDate.now(
                java.time.ZoneId.of("Asia/Kolkata")
        );

        MaintenanceBulkRequestDTO  requestDTO = new MaintenanceBulkRequestDTO();
        requestDTO.setBillMonth(today.getMonthValue());
        requestDTO.setBillYear(today.getYear());

        var generatedBills = maintenanceService.generateMaintenanceBillsForAllFlats(requestDTO);

        System.out.println
                ("Monthly maintenance scheduler completed! Bill generated : "
                        + generatedBills.size());
    }

    @Scheduled(
            cron = "0 0 0 * * *",
            zone = "Asia/Kolkata"
    )

    public void calculateDailyLateFee() {
        int updatedBills = maintenanceService.calculateLateFee();

        System.out.println(" Daily late-fee calculation completed! Bills updated: " + updatedBills);
    }
}
