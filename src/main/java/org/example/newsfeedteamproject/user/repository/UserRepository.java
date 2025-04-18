package org.example.newsfeedteamproject.user.repository;

import org.example.newsfeedteamproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByWithdrawnFalse();

    boolean existsByEmail(String email);

    boolean existsByUserRefId(String UserRefId);

    boolean existsByPhone(String phone);
}
