package org.example.newsfeedteamproject.commentLikes.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.commentLikes.dto.CommentLikeResponseDto;
import org.example.newsfeedteamproject.commentLikes.service.CommentLikeService;
import org.example.newsfeedteamproject.global.consts.Const;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


/**
 * 댓글 좋아요 관련 Http 요청을 처리 하는 컨트롤러 클래스입니다.
 * 댓글에 좋아요를 추가하거나 제거하는 기능을 담당합니다.
 */
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;
    private final UserRepository userRepository;

    /**
     * 특정 댓글에 대해 사용자의 좋아요 상태를 토글합니다.
     * 이미 좋아요를 누른 경우는 취소되고, 누르지 않은 경우는 좋아요가 추가됩니다.
     *
     * @param commentId 좋아요를 토글할 대상 댓글의 ID
     * @param userId 세션에서 가져온 현재 로그인한 사용자의 ID
     * @return {@link CommentLikeResponseDto} 객체로, 성공 여부와 총 좋아요 수를 포함합니다.
     * @throws ResponseStatusException 사용자가 존재하지 않을 경우 404 예외 발생
     */
    @PostMapping("/{commentId}/like")
    public CommentLikeResponseDto toggleLike(
            @PathVariable Long commentId,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ){
        User fromUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."));
        return commentLikeService.toggleCommentLike(fromUser, commentId);
    }

}
