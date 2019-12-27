package com.exercise.security.repository;

import com.exercise.security.entity.UserCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserCustom,Long> {

    boolean existsByUsername(String email);

    Optional<UserCustom> findByUsername(String email);

    void deleteByUsername(String email);
}
