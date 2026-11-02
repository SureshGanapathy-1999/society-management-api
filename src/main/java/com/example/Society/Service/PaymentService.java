package com.example.Society.Service;

import com.example.Society.Config.MaintenanceStatus;
import com.example.Society.Config.PaymentMode;
import com.example.Society.Config.PaymentStatus;
import com.example.Society.DTO.PaymentRequestDTO;
import com.example.Society.DTO.PaymentResponseDTO;
import com.example.Society.Model.Maintenance;
import com.example.Society.Model.Payment;
import com.example.Society.Repository.MaintenanceRepository;
import com.example.Society.Repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MaintenanceRepository maintenanceRepository;

    public PaymentService(PaymentRepository paymentRepository, MaintenanceRepository maintenanceRepository) {
        this.paymentRepository = paymentRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    @Transactional
    public PaymentResponseDTO recordOfflinePayment(PaymentRequestDTO paymentRequestDTO, String receivedBy) {

        if(paymentRequestDTO.getPaymentMode() != PaymentMode.CASH && paymentRequestDTO.getPaymentMode() != PaymentMode.BANK_TRANSFER) {
            throw new IllegalArgumentException("Only cash and Bank Transfer payments are supported");
        }

        if(paymentRequestDTO.getAmountPaid() == null || paymentRequestDTO.getAmountPaid().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount paid must be greater than zero");
        }

        Maintenance maintenance = maintenanceRepository.findById(paymentRequestDTO.getMaintenanceId())
                .orElseThrow(() -> new IllegalArgumentException("Maintenance bill not found"));

        BigDecimal totalPaid = paymentRepository.sumAmountPaidByMaintenanceIdAndPaymentStatus(maintenance.getId() , PaymentStatus.SUCCESS);

        if(totalPaid == null){
            totalPaid = BigDecimal.ZERO;
        }

        BigDecimal outStanding = maintenance.getTotalAmount().subtract(totalPaid);

        if (outStanding.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("This maintenance bill is already fully paid");
        }

        if(paymentRequestDTO.getAmountPaid().compareTo(outStanding) > 0){
            throw new IllegalArgumentException("Payment amount cannot exceed the outstanding balance");
        }

        Payment payment = new Payment();
        payment.setPaymentDate(java.time.LocalDateTime.now());
        payment.setMaintenance(maintenance);
        payment.setAmountPaid(paymentRequestDTO.getAmountPaid());
        payment.setPaymentMode(paymentRequestDTO.getPaymentMode());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTransactionRef(paymentRequestDTO.getTransactionRef());
        payment.setRemarks(paymentRequestDTO.getRemarks());
        payment.setReceivedBy(receivedBy);

        Payment savedPayment = paymentRepository.save(payment);

        BigDecimal updatedTotalPaid = totalPaid.add(paymentRequestDTO.getAmountPaid());

        if(updatedTotalPaid.compareTo(maintenance.getTotalAmount()) >= 0){
            maintenance.setMaintenanceStatus(MaintenanceStatus.PAID);
        }else if(maintenance.getDueDate().isBefore(java.time.LocalDate.now(java.time.ZoneId.of("Asia/Kolkata")))){
            maintenance.setMaintenanceStatus(MaintenanceStatus.OVERDUE);
        } else{
            maintenance.setMaintenanceStatus(MaintenanceStatus.PENDING);
        }

        maintenanceRepository.save(maintenance);

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setId(savedPayment.getId());
        paymentResponseDTO.setMaintenanceId(maintenance.getId());
        paymentResponseDTO.setBillNumber(maintenance.getBillNumber());
        paymentResponseDTO.setFlatId(maintenance.getFlat().getId());
        paymentResponseDTO.setBillMonth(maintenance.getBillMonth());
        paymentResponseDTO.setBillYear(maintenance.getBillYear());
        paymentResponseDTO.setAmountPaid(savedPayment.getAmountPaid());
        paymentResponseDTO.setPaymentDate(savedPayment.getPaymentDate());
        paymentResponseDTO.setPaymentStatus(savedPayment.getPaymentStatus());
        paymentResponseDTO.setPaymentMode(savedPayment.getPaymentMode());
        paymentResponseDTO.setTransactionRef(savedPayment.getTransactionRef());
        paymentResponseDTO.setRemarks(savedPayment.getRemarks());
        paymentResponseDTO.setCreatedAt(savedPayment.getCreatedAt());
        paymentResponseDTO.setReceivedBy(savedPayment.getReceivedBy());

        return paymentResponseDTO;
    }
}
