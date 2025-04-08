package org.example.newsfeedteamproject.comment.repository;

import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Post_ID로 특정 포스트의 댓글을 찾아주는 메소드입니다.
     * @param postId
     * @param pageable
     * @return
     */

    Page<Comment> findByPostId(Long postId, Pageable pageable);

    /**
     * User_ID로 특정 유저의 댓글을 찾아주는 메소드입니다.
     * @param userId
     * @param pageable
     * @return
     */

    Page<Comment> findByUserId(Long userId, Pageable pageable);
}