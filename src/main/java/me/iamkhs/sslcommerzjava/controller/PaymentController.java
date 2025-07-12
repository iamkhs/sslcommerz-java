package me.iamkhs.sslcommerzjava.controller;

import lombok.RequiredArgsConstructor;
import me.iamkhs.sslcommerzjava.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiatePayment() {
        try {
            String url = paymentService.initiatePayment();
            return ResponseEntity.ok(Map.of("redirect_url", url));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error initiating payment: " + e.getMessage());
        }
    }

    @PostMapping("/success")
    public ResponseEntity<?> paymentSuccess(@RequestParam Map<String, String> allParams) {
        String valId = allParams.get("val_id");
        if (valId == null) return ResponseEntity.badRequest().body("Missing val_id");

        try {
            Map<String, Object> validated = paymentService.validatePayment(valId);
            return ResponseEntity.ok("✅ Payment Validated Successfully!\n" + validated);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Failed to validate payment: " + e.getMessage());
        }
    }

    @PostMapping("/fail")
    public ResponseEntity<String> paymentFail(@RequestParam Map<String, String> allParams) {
        return ResponseEntity.ok("❌ Payment Failed! " + allParams);
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> paymentCancel(@RequestParam Map<String, String> allParams) {
        return ResponseEntity.ok("⚠️ Payment Cancelled! " + allParams);
    }
}
