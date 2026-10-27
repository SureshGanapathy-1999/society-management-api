
package com.example.Society;

import com.example.Society.DTO.MaintenanceBulkRequestDTO;
import com.example.Society.Service.MaintenanceScheduler;
import com.example.Society.Service.MaintenanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MaintenanceSchedulerTest {

    @Mock
    private MaintenanceService maintenanceService;

    @InjectMocks
    private MaintenanceScheduler maintenanceScheduler;

    @Test
    void shouldGenerateBillsForCurrentMonthAndYear() {

        when(maintenanceService.generateMaintenanceBillsForAllFlats(
                any(MaintenanceBulkRequestDTO.class)))
                .thenReturn(Collections.emptyList());

        maintenanceScheduler.generateMonthlyMaintenanceBills();

        ArgumentCaptor<MaintenanceBulkRequestDTO> captor =
                ArgumentCaptor.forClass(MaintenanceBulkRequestDTO.class);

        verify(maintenanceService)
                .generateMaintenanceBillsForAllFlats(captor.capture());

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        assertEquals(today.getMonthValue(),
                captor.getValue().getBillMonth());

        assertEquals(today.getYear(),
                captor.getValue().getBillYear());
    }
}
