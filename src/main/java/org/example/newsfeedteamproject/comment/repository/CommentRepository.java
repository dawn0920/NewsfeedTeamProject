package org.example.newsfeedteamproject.comment.repository;

import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.commentLikes.entity.CommentLikes;
import org.example.newsfeedteamproject.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Post_ID로 특정 포스트의 댓글을 찾아주는 메소드입니다.
     * @param postId
     * @param pageable
     * @return
     */

    Slice<Comment> findByPostId(Long postId, Pageable pageable);

    /**
     * User_ID로 특정 유저의 댓글을 찾아주는 메소드입니다.
     * @param userId
     * @param pageable
     * @return
     */

    Slice<Comment> findByUserId(Long userId, Pageable pageable);

    /**
     * 특정 코멘트 아이디를 유저 정보와 포스트 정보를 함께 뽑아주고 있습니다. 이때 조건은 포스트 id와 코멘트 id입니다.
     * @param postId
     * @return
     */

    List<Comment> findByPostId(Long postId);

    // comment의 like 개수를 리스트로 받음
    int countByToComment(Comment comment);

    // post의 comment의 개수를 받음
    int countByPost(Post post);
}