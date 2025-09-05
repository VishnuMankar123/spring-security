package com.example.repo;

import com.example.entity.PasswordResetToken;
import com.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUserEntity(UserEntity user);
    Optional<PasswordResetToken> findByUserEntity(UserEntity user);
}