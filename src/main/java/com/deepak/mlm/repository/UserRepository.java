package com.deepak.mlm.repository;

import com.deepak.mlm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is our UserRepository class, with the help of this we can use pre-defined method and also we can create custom method.
 * @author Sudo-Deepak
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
