package com.example.service;

import com.example.entity.PasswordResetToken;
import com.example.entity.UserEntity;
import com.example.repo.PasswordResetTokenRepository;
import com.example.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final long EXPIRATION = 60 * 60 * 1000;
    private static final int CODE_LENGTH = 6;// 1 hour

    public String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates between 100000 and 999999
        return String.valueOf(code);
    }

    public void createPasswordResetTokenForUser(UserEntity user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setUserEntity(user);
        myToken.setToken(token);
        myToken.setExpiryDate(Instant.now().plusMillis(EXPIRATION));
        tokenRepository.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> passToken = tokenRepository.findByToken(token);

        if (passToken.isEmpty()) {
            return "invalid";
        }

        if (passToken.get().getExpiryDate().isBefore(Instant.now())) {
            return "expired";
        }

        return "valid";
    }

    public void sendPasswordResetEmail(String email) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            String token = generateRandomCode();
            createPasswordResetTokenForUser(user, token);

            String resetUrl = token;

            // Debug prints
            System.out.println("Generated reset token: " + token);
            System.out.println("Password reset URL: " + resetUrl);
            System.out.println("Preparing to send password reset email to: " + user.getEmail());

            SimpleMailMessage emailMessage = new SimpleMailMessage();
            emailMessage.setTo(user.getEmail());
            emailMessage.setSubject("Password Reset Request");
            emailMessage.setText("To reset your password, click the link below:\n" + resetUrl +
                    "\n\nThis link will expire in 1 hour.");

            // Before sending
            System.out.println("Sending email...");
            try {
                mailSender.send(emailMessage); // Simulate delay
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to send email: " + e.getMessage());
            }
            // After sending
            System.out.println("Password reset email successfully sent to: " + user.getEmail());
        } else {
            System.out.println("No user found with email: " + email);
        }
    }



    public void changeUserPassword(UserEntity user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.deleteByUserEntity(user);
    }

    public Optional<UserEntity> getUserByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token)
                .map(PasswordResetToken::getUserEntity);
    }
}