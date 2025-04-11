package org.example.newsfeedteamproject.commentLikes.repository;

import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.commentLikes.entity.CommentLikes;
import org.example.newsfeedteamproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 댓글 좋아요 엔티티에 대한 데이터베이스 접근을 담당하는 레포지토리 인터페이스입니다.
 */
@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikes, Long> {

    /**
     * 특정 사용자와 댓글의 좋아요 정보를 조회합니다.
     *
     * @param fromUser 좋아요를 누른 사용자
     * @param toComment 좋아요가 눌린 댓글
     * @return 해당 사용자와 댓글 간의 좋아요 정보가 존재하면 Optional로 반환
     */
    Optional<CommentLikes> findByFromUserAndToComment(User fromUser, Comment toComment);

    /**
     * 특정 댓글에 대한 총 좋아요 수를 반환합니다.
     *
     * @param toComment 대상 댓글
     * @return 해당 댓글에 달린 좋아요 수
     */
    int countByToComment(Comment toComment);

    /**
     * 좋아요 갯수를 가져옵니다.
     */

    Long countByToComment_Id(Long commentId);
}
