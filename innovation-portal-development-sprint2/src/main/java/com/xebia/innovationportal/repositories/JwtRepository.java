package com.xebia.innovationportal.repositories;

import com.xebia.innovationportal.entities.Jti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRepository extends JpaRepository<Jti, Long> {

    Jti findByValue(String jti);

    void deleteByValue(String jti);

    Optional<Jti> findByUserId(Long userId);
}
