package com.xebia.innovationportal.services.impl;

import com.xebia.innovationportal.entities.Authority;
import com.xebia.innovationportal.enums.Role;
import com.xebia.innovationportal.repositories.AuthorityRepository;
import com.xebia.innovationportal.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;

    @Transactional
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Transactional(readOnly = true)
    public Set<Authority> findByRoleIn(Set<Role> roles) {
        return authorityRepository.findByRoleIn(roles);
    }
}
