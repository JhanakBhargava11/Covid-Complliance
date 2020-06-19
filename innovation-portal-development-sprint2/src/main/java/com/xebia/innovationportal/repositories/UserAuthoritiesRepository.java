package com.xebia.innovationportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xebia.innovationportal.entities.UsersAuthorities;

@Repository
public interface UserAuthoritiesRepository extends JpaRepository<UsersAuthorities, Long> {

}
