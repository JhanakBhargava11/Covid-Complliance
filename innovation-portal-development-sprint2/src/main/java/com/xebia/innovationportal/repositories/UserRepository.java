package com.xebia.innovationportal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xebia.innovationportal.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryJPA {
    Optional<User> findByEmail(String email);

}
