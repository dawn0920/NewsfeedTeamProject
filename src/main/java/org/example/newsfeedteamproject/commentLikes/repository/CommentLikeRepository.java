package org.example.newsfeedteamproject.commentLikes.repository;

import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.commentLikes.entity.CommentLikes;
import org.example.newsfeedteamproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikes, Long> {


    Optional<CommentLikes> findByFromUserAndToComment(User fromUser, Comment toComment);

    int countByToComment(Comment toComment);
}
