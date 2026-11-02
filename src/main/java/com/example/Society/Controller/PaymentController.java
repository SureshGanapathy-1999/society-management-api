package com.example.Society.Controller;


import com.example.Society.DTO.PaymentRequestDTO;
import com.example.Society.DTO.PaymentResponseDTO;
import com.example.Society.Service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIETY_ADMIN')")
    @PostMapping("/offline")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDTO recordOfflinePayment(
            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO,
            Authentication authentication) {

        return paymentService.recordOfflinePayment(
                paymentRequestDTO,
                authentication.getName()
        );
    }
}
