package org.example.newsfeedteamproject.PostLikes.repository;

import org.example.newsfeedteamproject.PostLikes.entity.PostLikes;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.user.entity.Follow;
import org.example.newsfeedteamproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeReopsitory extends JpaRepository<PostLikes, Long> {

    // 좋아요 여부
    // boolean existsByFromUserAndToPost(User fromUser, Post toPost);

    // 좋아요를 누른 유저 수
    // int countByToUser(User fromUser); -> 특정 사용자가 좋아요를 누른 횟수

    // 게시글에 좋아요를 누른 유저 수
    int countByToPost(Post toPost);

    // 좋아요 취소
    // void deleteByFromUserAndToPost(User fromUser, Post toPost);

    // 특정 사용자의 팔로우 관계 조회
    Optional<PostLikes> findByFromUserAndToPost(User fromUser, Post toPost);

    // Long FromUser(User fromUser);
}
