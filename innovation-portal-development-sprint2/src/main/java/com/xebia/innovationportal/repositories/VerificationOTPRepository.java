package com.xebia.innovationportal.repositories;

import com.xebia.innovationportal.entities.OTPVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationOTPRepository extends JpaRepository<OTPVerification, String> {

    Optional<OTPVerification> findByOtp(final String otp);

    Optional<OTPVerification> findByUserId(final Long userId);

}
