package com.xebia.innovationportal.services.impl;

import static com.xebia.innovationportal.constants.ExceptionConstants.USER_NAME_NOT_AVAILABLE;
import static com.xebia.innovationportal.constants.ExceptionConstants.USER_NOT_PRESENT_ON_ZOHO;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xebia.innovationportal.entities.Authority;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.enums.Role;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.models.CreateUserRequest;
import com.xebia.innovationportal.models.StaffMetaData;
import com.xebia.innovationportal.models.ZohoUserData;
import com.xebia.innovationportal.repositories.UserRepository;
import com.xebia.innovationportal.services.AuthorityService;
import com.xebia.innovationportal.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user, Set<Role> roles) {
        if (user.getAuthorities() == null)
            user.updateAuthorities(authorityService.findByRoleIn(roles));
        return userRepository.save(user);
    }

    @Override
    public ZohoUserData getZohoUserData(CreateUserRequest request) {
        ZohoUserData zohoUserData = null;
        String zohoApiUrl = "https://people.zoho.com/people/api/forms/P_EmployeeView/records?authtoken=";
        String zohoAuthToken = "3ab0a1722b48eb4d9db8c69649e73fec";
        String url = zohoApiUrl + zohoAuthToken + "&searchColumn=EMPLOYEEMAILALIAS&searchValue=" + request.getEmail();
        try {
            StaffMetaData[] result = restTemplate.getForObject(url, StaffMetaData[].class);
            zohoUserData = setStaffDataToZohoUserdata(result[0]);
            if (zohoUserData.isDataPresent()) {
                User userData = zohoUserData.getUserData();
                userData.updateTimeZone(request.getTimezone());
                userData.updateCreationDate();
                zohoUserData.setUserData(userData);
                zohoUserData.setDataPresent(true);
            } else {
                zohoUserData.setUserData(null);
                zohoUserData.setDataPresent(false);
            }

        } catch (Exception e) {
            new GenericException(USER_NOT_PRESENT_ON_ZOHO, HttpStatus.NOT_FOUND.value());
        }
        return zohoUserData;
    }

    @Override
    public ZohoUserData setStaffDataToZohoUserdata(StaffMetaData staffMetaData) {
        ZohoUserData zohoUserData = new ZohoUserData();
        if (staffMetaData.getXebiaEmailId() == null) {
            zohoUserData.setDataPresent(false);
        } else {
            User user = new User.Builder(staffMetaData.getXebiaEmailId()).employeeCode(staffMetaData.getEmployeeID())
                    .location(staffMetaData.getLocation()).name(staffMetaData.getFullName())
                    .displayName(staffMetaData.getDisplayName()).designation(staffMetaData.getDesignation())
                    .enabled(false).build();

            zohoUserData.setUserData(user);
            zohoUserData.setDataPresent(true);
        }

        return zohoUserData;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException(USER_NAME_NOT_AVAILABLE));
            Set<Authority> authorities = user.getAuthorities();
            List<GrantedAuthority> grantedAuthorities = authorities.stream()
                    .map((Function<Authority, GrantedAuthority>) authority -> new SimpleGrantedAuthority(
                            authority.getRole().name()))
                    .collect(Collectors.toList());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                return new org.springframework.security.core.userdetails.User(email, user.getPassword(),
                        user.isEnabled(), true, true, true, grantedAuthorities);
            }
            return new org.springframework.security.core.userdetails.User(email, "", user.isEnabled(), true, true, true,
                    grantedAuthorities);
        } catch (Throwable throwable) {
            throw new UsernameNotFoundException(USER_NAME_NOT_AVAILABLE);
        }
    }
}
