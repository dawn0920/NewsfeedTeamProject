package org.example.newsfeedteamproject.user.repository;

import org.example.newsfeedteamproject.user.entity.Follow;
import org.example.newsfeedteamproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로우 여부
    boolean existsByFromUserAndToUser(User fromUser, User toUser);

    // 팔로우 하는 유저 수
    int countByToUser(User toUser);

    // 언팔로우
    void deleteByFromUserAndToUser(User fromUser, User toUser);

    // 특정 사용자의 팔로우 관계 조회
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

}
