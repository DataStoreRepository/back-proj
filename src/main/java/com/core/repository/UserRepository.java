package com.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.core.entity.UserMarket;

@Repository
public interface UserRepository extends JpaRepository<UserMarket, Long> {
    boolean existsByEmail(String email);

    UserDetails findByEmail(String email);

    @Query("SELECT u FROM UserMarket u WHERE u.email = :email")
    Optional<UserMarket> findUserByEmail(String email);
}
