package com.xebia.innovationportal.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Table(name = "otp_verification")
public class OTPVerification {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "otp")
    private String otp;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "used")
    private boolean isUsed;

    public OTPVerification() {
    }

    public OTPVerification(final String otp, final User user) {
        Calendar calendar = Calendar.getInstance();

        this.otp = otp;
        this.user = user;
        this.createdDate = new Date(calendar.getTime().getTime());
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public void updateOTP(final String otp) {
        this.otp = otp;
        this.createdDate = new Date(Calendar.getInstance().getTime().getTime());
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(final String otp) {
        this.otp = otp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

}
