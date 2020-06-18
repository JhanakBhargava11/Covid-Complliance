package com.xebia.innovationportal.repositories;

import com.xebia.innovationportal.entities.Authority;
import com.xebia.innovationportal.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Set<Authority> findByRoleIn(Set<Role> roles);
}
