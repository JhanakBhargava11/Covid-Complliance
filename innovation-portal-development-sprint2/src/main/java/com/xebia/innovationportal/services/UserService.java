package com.xebia.innovationportal.services;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.enums.Role;
import com.xebia.innovationportal.models.CreateUserRequest;
import com.xebia.innovationportal.models.StaffMetaData;
import com.xebia.innovationportal.models.ZohoUserData;

public interface UserService extends UserDetailsService {

    Optional<User> findUserByEmail(final String email);

    default User transform(CreateUserRequest request) {
        return new User.Builder(request.getEmail()).location(request.getCountry()).displayName(request.getDisplayName())
                .name(request.getName()).timezone(request.getTimezone()).employeeCode(request.getEmployeeCode())
                .enabled(false).build();
    }

    @Transactional
    default User save(User user) {
        return save(user, Collections.singleton(Role.ROLE_USER));
    }

    User save(User user, Set<Role> roles);

    ZohoUserData getZohoUserData(final CreateUserRequest createUserRequest);

    ZohoUserData setStaffDataToZohoUserdata(final StaffMetaData staffMetaData);
}
