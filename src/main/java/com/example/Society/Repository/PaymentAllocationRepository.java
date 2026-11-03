package com.example.Society.Repository;

import com.example.Society.Config.PaymentStatus;
import com.example.Society.Model.PaymentAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository

public interface PaymentAllocationRepository extends JpaRepository<PaymentAllocation, Long> {
    @Query("""
        SELECT SUM(pa.amountAllocated)
        FROM PaymentAllocation pa
        WHERE pa.maintenance.id = :maintenanceId
        AND pa.payment.paymentStatus = :paymentStatus
        """)
    BigDecimal sumAllocatedAmountByMaintenanceIdAndPaymentStatus(
            @Param("maintenanceId") Long maintenanceId,
            @Param("paymentStatus") PaymentStatus paymentStatus
    );
}
