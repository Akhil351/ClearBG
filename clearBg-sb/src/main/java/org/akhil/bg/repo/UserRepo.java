package org.akhil.bg.repo;

import org.akhil.bg.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByClerkId(String clerkId);
    boolean existsByClerkId(String clerkId);
}
