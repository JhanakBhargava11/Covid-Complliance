package com.xebia.innovationportal.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jti")
public class Jti extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @Column(name = "expired_at")
    private LocalDateTime expiredOn;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Jti() {
    }

    public Jti(String value, LocalDateTime expiredOn) {
        this.value = value;
        this.expiredOn = expiredOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(LocalDateTime expiredOn) {
        this.expiredOn = expiredOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
