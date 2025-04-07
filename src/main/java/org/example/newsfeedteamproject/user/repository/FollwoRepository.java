package org.example.newsfeedteamproject.user.repository;

import org.example.newsfeedteamproject.user.entity.Follwo;
import org.example.newsfeedteamproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollwoRepository extends JpaRepository<Follwo, Long> {

    // 팔로우 여부
    boolean existsByFromUserAndToUser(Follwo fromUser, Follwo toUser);

    // 팔로우 하는 유저 수
    int countByToUser(Follwo toUser);

    // 언팔로우
    void deleteByFromUserAndToUser(Follwo fromUser, Follwo toUser);
}
