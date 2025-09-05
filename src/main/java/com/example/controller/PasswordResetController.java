package com.example.controller;

import com.example.entity.ResetPasswordRequest;
import com.example.entity.UserEntity;
import com.example.repo.UserRepository;
import com.example.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            if (!userRepository.existsByEmail(email)) {
                // Return success even if email doesn't exist for security
                return ResponseEntity.ok().body(Map.of("message", "User Not Exist with this email"));
            }

            passwordResetService.sendPasswordResetEmail(email);
            return ResponseEntity.ok().body(Map.of("message", "If the email exists, a reset link has been sent"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error sending reset email"));
        }
    }

    @GetMapping("/validate-reset-token")
    public ResponseEntity<?> validateResetToken(@RequestParam String token) {
        String result = passwordResetService.validatePasswordResetToken(token);

        if (result.equals("valid")) {
            return ResponseEntity.ok().body(Map.of("valid", true));
        } else if (result.equals("expired")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Token expired"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        String tokenValidation = passwordResetService.validatePasswordResetToken(request.getToken());

        if (!tokenValidation.equals("valid")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired token"));
        }

        Optional<UserEntity> userOptional = passwordResetService.getUserByPasswordResetToken(request.getToken());

        if (userOptional.isPresent()) {
            passwordResetService.changeUserPassword(userOptional.get(), request.getNewPassword());
            return ResponseEntity.ok().body(Map.of("message", "Password reset successfully"));
        }

        return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
    }

}