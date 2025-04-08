package org.example.newsfeedteamproject.follow.repository;

import org.example.newsfeedteamproject.follow.entity.Follow;
import org.example.newsfeedteamproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로우 여부
    boolean existsByFromUserAndToUser(User fromUser, User toUser);

    // 팔로우 하는 유저 수
    int countByToUser(User toUser);

    // 언팔로우
    void deleteByFromUserAndToUser(User fromUser, User toUser);
}
