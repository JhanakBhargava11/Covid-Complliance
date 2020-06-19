package com.xebia.innovationportal.models;

import javax.validation.constraints.Email;

import org.springframework.data.domain.Pageable;

import com.xebia.innovationportal.enums.Role;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSearchRequest {

    private String name;

    @Email
    private String email;

    private Pageable pageable;

    private Role role;

    private UserSearchRequest(String name, String email, Role role, Pageable pageable) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.pageable = pageable;
    }

    public static UserSearchRequest of(String name, String email, Role role, Pageable pageable) {
        return new UserSearchRequest(name, email, role, pageable);

    }

}
