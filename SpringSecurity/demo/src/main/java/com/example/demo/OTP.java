package com.example.demo;

public class OTP {
    private String otp;

    public OTP(String otp) {
        this.otp = otp;
        System.out.println(this.otp);
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
