package org.example.newsfeedteamproject.commentLikes.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.PostLikes.entity.PostLikes;
import org.example.newsfeedteamproject.PostLikes.repository.PostLikeReopsitory;
import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.comment.repository.CommentRepository;
import org.example.newsfeedteamproject.commentLikes.dto.CommentLikeResponseDto;
import org.example.newsfeedteamproject.commentLikes.entity.CommentLikes;
import org.example.newsfeedteamproject.commentLikes.repository.CommentLikeRepository;
import org.example.newsfeedteamproject.global.error.ExceptionCode;
import org.example.newsfeedteamproject.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeReopsitory postLikeReopsitory;

    @Transactional
    public CommentLikeResponseDto toggleCommentLike(User fromUser, Long commentId) {

        Comment toComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"댓글을 찾을 수 없습니다."));

        Optional<CommentLikes> existingLiked = commentLikeRepository.findByFromUserAndToComment(fromUser,toComment);

        if(existingLiked.isPresent()){
            commentLikeRepository.delete(existingLiked.get());

            toComment.decreaseLike();
            commentRepository.save(toComment);

            int currentLikeCount = commentLikeRepository.countByToComment(toComment);
            return new CommentLikeResponseDto(false, currentLikeCount);
        } else {
            commentLikeRepository.save(new CommentLikes(fromUser, toComment));

            toComment.increaseLike();
            commentRepository.save(toComment);

            int currentLikeCount = commentLikeRepository.countByToComment(toComment);
            return new CommentLikeResponseDto(true, currentLikeCount);
        }

    }
}
