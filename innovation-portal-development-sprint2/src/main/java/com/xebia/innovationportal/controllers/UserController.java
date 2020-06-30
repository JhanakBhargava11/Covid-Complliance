package com.xebia.innovationportal.controllers;

import com.xebia.innovationportal.entities.Jti;
import com.xebia.innovationportal.entities.OTPVerification;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.models.BaseResponse;
import com.xebia.innovationportal.models.CreateUserRequest;
import com.xebia.innovationportal.models.CreateUserResponse;
import com.xebia.innovationportal.models.UserResponse;
import com.xebia.innovationportal.models.ZohoUserData;
import com.xebia.innovationportal.services.JWTService;
import com.xebia.innovationportal.services.UserService;
import com.xebia.innovationportal.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Optional;

import static com.xebia.innovationportal.constants.CommonConstants.*;
import static com.xebia.innovationportal.constants.ExceptionConstants.*;
import static com.xebia.innovationportal.constants.RestConstants.INNOVATION_PORTAL_API;
import static com.xebia.innovationportal.constants.RestConstants.UserDetails.*;

@RestController
@RequestMapping(INNOVATION_PORTAL_API)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private JWTService jwtService;

    @PostMapping(USER_OTP)
    public ResponseEntity<BaseResponse> getOTP(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = null;
        Long userId;
        Optional<User> userEntry = userService.findUserByEmail(createUserRequest.getEmail());

        if (userEntry.isPresent()) {
            verificationService.sendAndStoreOTP(userEntry.get(), createUserRequest);
            userId = userEntry.get().getId();
        } else {
            ZohoUserData zohoUserData = userService.getZohoUserData(createUserRequest);
            if (zohoUserData != null && zohoUserData.isDataPresent()) {
                user = userService.save(zohoUserData.getUserData());
                verificationService.sendAndStoreOTP(user, createUserRequest);

                userId = user.getId();
            } else {
                throw new GenericException(USER_NOT_PRESENT_ON_ZOHO, HttpStatus.NOT_FOUND.value());
            }
        }

        return ResponseEntity.accepted().body(new BaseResponse<>(new CreateUserResponse(userId)));
    }

    @GetMapping(USER_OTP_VERIFY)
    public ResponseEntity<BaseResponse> verifyOTP(@RequestParam String otp) {
        Optional<OTPVerification> otpVerification = verificationService.findOTPVerificationByOTP(otp);

        if (!otpVerification.isPresent()) {
            return new ResponseEntity(new BaseResponse(HttpStatus.UNAUTHORIZED.value(), OTP_INVALID), HttpStatus.UNAUTHORIZED);
        }
        if (otpVerification.get().isUsed()) {
            return new ResponseEntity(new BaseResponse(HttpStatus.UNAUTHORIZED.value(), OTP_ALREADY_USED), HttpStatus.UNAUTHORIZED);
        }
        User user = otpVerification.get().getUser();
        Calendar calendar = Calendar.getInstance();

        if ((otpVerification.get().getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            return new ResponseEntity(new BaseResponse(HttpStatus.UNAUTHORIZED.value(), OTP_EXPIRED), HttpStatus.UNAUTHORIZED);
        }

        //Updating user and OTP details and generating authorization token
        otpVerification.get().setUsed(true);
        verificationService.saveOTPVerification(otpVerification.get());

        user.updateEnabled(true);
        String emailId = userService.save(user).getEmail();

        Optional<String> authToken = null;
        if (user.isEnabled() == true) {
            authToken = jwtService.createAndSaveAuthToken(emailId);
        } else {
            throw new GenericException(USER_NOT_VERIFIED, HttpStatus.CONFLICT.value());
        }

        return new ResponseEntity<>(new BaseResponse<>(authToken.get(), UserResponse.of(user)), HttpStatus.OK);
    }

    @GetMapping(USER_AUTH_TOKEN_VERIFY)
    public ResponseEntity<BaseResponse<Boolean>> verifyUserAuthenticationToken(@RequestParam String emailId) {
        Optional<User> userEntry = userService.findUserByEmail(emailId);
        boolean result = false;

        if (userEntry.isPresent()) {
            Optional<Jti> jti = jwtService.findByUserId(userEntry.get().getId());
            if (jti.isPresent()) {
                result = jwtService.isValidJWTToken(jti.get());
            }
        } else {
            throw new GenericException(USER_EMAIL_NOT_PRESENT, HttpStatus.NOT_FOUND.value());
        }
        return ResponseEntity.ok(new BaseResponse<Boolean>(result));
    }
}