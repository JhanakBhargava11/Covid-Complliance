package com.xebia.innovationportal.services;

import com.xebia.innovationportal.entities.Authority;
import com.xebia.innovationportal.enums.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface AuthorityService {

    public Authority save(Authority authority);

    public Set<Authority> findByRoleIn(Set<Role> roles);
}
