package io.goorm.ainfras.target.domain.User.repository;

import io.goorm.ainfras.target.domain.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);

    @Query("SELECT u.modifiedDate FROM User u WHERE u.id = :id")
    LocalDateTime findModifiedDate(Long id);
}
