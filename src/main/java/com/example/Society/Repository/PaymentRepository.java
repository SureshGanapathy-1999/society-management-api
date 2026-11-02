package com.example.Society.Repository;

import com.example.Society.Config.PaymentStatus;
import com.example.Society.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("""
    select SUM(p.amountPaid) from Payment p
    where p.maintenance.id = :maintenanceId 
    and p.paymentStatus = :paymentStatus
        """)
    BigDecimal sumAmountPaidByMaintenanceIdAndPaymentStatus(
            @Param("maintenanceId") Long maintenanceId,
            @Param("paymentStatus") PaymentStatus paymentStatus);

}
