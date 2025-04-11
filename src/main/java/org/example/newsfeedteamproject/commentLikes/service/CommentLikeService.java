package org.example.newsfeedteamproject.commentLikes.service;


import org.example.newsfeedteamproject.commentLikes.dto.CommentLikeResponseDto;
import org.example.newsfeedteamproject.commentLikes.dto.CountCommentLike;
import org.example.newsfeedteamproject.user.entity.User;

/**
 * 댓글 좋아요 기능에 대한 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 */
public interface CommentLikeService {

    /**
     * 특정 댓글에 대해 사용자의 좋아요 상태를 토글합니다.
     * 이미 좋아요를 눌렀다면 취소하고, 아니라면 좋아요를 추가합니다.
     *
     * @param fromUser 좋아요를 누른 사용자
     * @param commentId 좋아요를 토글할 댓글의 ID
     * @return 처리 결과와 댓글의 좋아요 수를 담은 응답 DTO
     */
    CommentLikeResponseDto toggleCommentLike(User fromUser, Long commentId);

    CountCommentLike countCommentLikeService(Long commentId);
}
