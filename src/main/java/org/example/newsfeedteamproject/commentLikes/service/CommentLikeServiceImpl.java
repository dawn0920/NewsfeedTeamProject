package org.example.newsfeedteamproject.commentLikes.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.comment.repository.CommentRepository;
import org.example.newsfeedteamproject.commentLikes.dto.CommentLikeResponseDto;
import org.example.newsfeedteamproject.commentLikes.entity.CommentLikes;
import org.example.newsfeedteamproject.commentLikes.repository.CommentLikeRepository;
import org.example.newsfeedteamproject.global.error.CustomException;
import org.example.newsfeedteamproject.global.error.ExceptionCode;
import org.example.newsfeedteamproject.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * 댓글 좋아요 기능의 비즈니스 로직을 구현한 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    /**
     * 댓글에 대한 좋아요를 토글합니다.
     * 사용자가 이미 해당 댓글에 좋아요를 눌렀다면 좋아요를 취소하고, 누르지 않았다면 좋아요를 추가합니다.
     *
     * @param fromUser 좋아요를 수행한 사용자
     * @param commentId 좋아요를 토글할 대상 댓글의 ID
     * @return 처리 결과 및 현재 댓글의 좋아요 수를 포함한 응답 DTO
     * @throws ResponseStatusException 댓글이 존재하지 않을 경우 404 오류 발생
     */
    @Transactional
    public CommentLikeResponseDto toggleCommentLike(User fromUser, Long commentId) {

        Comment toComment = commentRepository.findById(commentId)
                .orElseThrow(() ->  new CustomException(ExceptionCode.COMMENT_NOT_FOUND));

        Optional<CommentLikes> existingLiked = commentLikeRepository.findByFromUserAndToComment(fromUser,toComment);

        if(existingLiked.isPresent()){
            //좋아요 취소
            commentLikeRepository.delete(existingLiked.get());
            toComment.decreaseLike();
            commentRepository.save(toComment);

            int currentLikeCount = commentLikeRepository.countByToComment(toComment);
            return new CommentLikeResponseDto(false, currentLikeCount);
        } else {
            //좋아요 추가
            commentLikeRepository.save(new CommentLikes(fromUser, toComment));
            toComment.increaseLike();
            commentRepository.save(toComment);

            int currentLikeCount = commentLikeRepository.countByToComment(toComment);
            return new CommentLikeResponseDto(true, currentLikeCount);
        }

    }
}
