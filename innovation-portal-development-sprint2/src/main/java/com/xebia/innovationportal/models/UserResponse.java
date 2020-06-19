package com.xebia.innovationportal.models;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xebia.innovationportal.entities.Authority;
import com.xebia.innovationportal.entities.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private Long userId;
    private String emailId;
    private String name;
    private String displayName;
    private Location location;
    private boolean enabled;
    private String role;

    public static UserResponse of(final User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.userId = user.getId();
        userResponse.emailId = user.getEmail();
        userResponse.name = user.getName();
        userResponse.displayName = user.getDisplayName();
        userResponse.location = new Location(user.getLocation(), user.getTimezone());
        userResponse.enabled = user.isEnabled();
        List<Authority> roles = user.getAuthorities().stream().collect(Collectors.toList());
        userResponse.setRole(roles.get(0).getRole().description());
        return userResponse;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String string) {
        this.role = string;
    }

    public static class Location {
        private String country;
        private String timeZone;

        public Location(String country, String timeZone) {
            this.country = country;
            this.timeZone = timeZone;
        }

        public String getCountry() {
            return country;
        }

        public String getTimeZone() {
            return timeZone;
        }
    }
}
