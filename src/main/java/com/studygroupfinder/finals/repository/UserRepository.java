package com.studygroupfinder.finals.repository;

import com.studygroupfinder.finals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.joinedGroups WHERE u.id = :id")
    Optional<User> findByIdWithJoinedGroups(@Param("id") Long id);
}

