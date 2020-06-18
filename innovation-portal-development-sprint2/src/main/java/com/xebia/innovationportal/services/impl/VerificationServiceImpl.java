package com.xebia.innovationportal.services.impl;

import com.xebia.innovationportal.entities.OTPVerification;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.models.CreateUserRequest;
import com.xebia.innovationportal.repositories.JwtRepository;
import com.xebia.innovationportal.repositories.VerificationOTPRepository;
import com.xebia.innovationportal.services.VerificationService;
import com.xebia.innovationportal.utils.EmailSender;
import com.xebia.innovationportal.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.xebia.innovationportal.constants.CommonConstants.LOGIN_CONFIRMATION;
import static com.xebia.innovationportal.constants.CommonConstants.OTP_SUCCESS_MESSAGE;

@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private VerificationOTPRepository verificationOTPRepository;

    @Autowired
    private JwtRepository jwtRepository;

    @Override
    public Optional<OTPVerification> findOTPVerificationByOTP(String otp) {
        Optional<OTPVerification> otpVerification = verificationOTPRepository.findByOtp(otp);

        return otpVerification;
    }

    @Override
    @Transactional
    public void saveAndUpdateOTPVerification(User user, String otp) {
        Optional<OTPVerification> otpVerificationData = findOTPVerificationByUserId(user.getId());

        if (otpVerificationData.isPresent()) {
            OTPVerification otpVerification = otpVerificationData.get();
            otpVerification.updateOTP(otp);
            otpVerification.setUsed(false);

            saveOTPVerification(otpVerification);
        } else {
            OTPVerification otpVerification = new OTPVerification(otp, user);
            otpVerification.setUsed(false);

            saveOTPVerification(otpVerification);
        }
    }

    @Override
    public void sendAndStoreOTP(final User userData, final CreateUserRequest request) {
        try {
            String otp = (String.valueOf(Utils.generateOTP(6)));
            saveAndUpdateOTPVerification(userData, otp);

            String text = OTP_SUCCESS_MESSAGE + otp;

            EmailSender.setEmail(request.getEmail(), LOGIN_CONFIRMATION, text);
        } catch (Exception re) {
            re.printStackTrace();
        }
    }

    @Override
    public OTPVerification saveOTPVerification(OTPVerification OTPVerification) {
        return verificationOTPRepository.save(OTPVerification);
    }

    @Override
    public Optional<OTPVerification> findOTPVerificationByUserId(final Long userId) {
        Optional<OTPVerification> otpVerification = verificationOTPRepository.findByUserId(userId);

        return otpVerification;
    }
}
