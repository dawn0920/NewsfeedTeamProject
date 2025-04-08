package org.example.newsfeedteamproject.comment.repository;

import org.example.newsfeedteamproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}