package com.xebia.innovationportal.services;

import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.entities.OTPVerification;
import com.xebia.innovationportal.models.CreateUserRequest;

import java.util.Optional;

public interface VerificationService {

    Optional<OTPVerification> findOTPVerificationByOTP(final String otp);

    void saveAndUpdateOTPVerification(final User user, final String otp);

    void sendAndStoreOTP(final User userData, final CreateUserRequest request);

    OTPVerification saveOTPVerification(final OTPVerification OTPVerification);

    Optional<OTPVerification> findOTPVerificationByUserId(final Long userId);
}
