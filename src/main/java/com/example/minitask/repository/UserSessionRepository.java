package com.example.minitask.repository;

import com.example.minitask.entity.UserSession;
import com.example.minitask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
    Optional<UserSession> findBySessionToken(String sessionToken);
}
