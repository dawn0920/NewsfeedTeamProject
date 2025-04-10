package org.example.newsfeedteamproject.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.comment.entity.CommentFactory;
import org.example.newsfeedteamproject.comment.repository.CommentRepository;
import org.example.newsfeedteamproject.global.error.CustomException;
import org.example.newsfeedteamproject.global.error.ExceptionCode;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.post.repository.PostRepository;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * id를 받아서 포스트를 찾아주고, 세션을 받아서 로그인 유무를 확인해준다음 댓글을 등록합니다.
     * @param requestDto
     * @param session
     * @return
     */

    @Transactional
    public CommentResponseDto addComment(Long userId,Long postId, CommentRequestDto requestDto) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ExceptionCode.FIND_NOT_INTERFACE));

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionCode.FIND_NOT_INTERFACE));

        Comment comment = CommentFactory.saveComment(post, user, requestDto);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    /**
     * 특정 포스트를 클릭하면 스크롤 방식으로 댓글을 보여줍니다.
     * @param postId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Slice<CommentResponseDto> getCommentsByPost(Long postId, Pageable pageable) {
        Slice<Comment> comments = commentRepository.findByPostId(postId, pageable);

        if(comments == null) {
            throw new CustomException(ExceptionCode.FIND_NOT_INTERFACE);
        }

        return comments.map(CommentResponseDto::new);
    }

    /**
     * 특정 유저를 클릭하면 그 유저가 작성한 댓글을 스크롤 방식으로 보여줍니다.
     * @param userId
     * @param pageable
     * @return
     */

    @Transactional(readOnly = true)
    public Slice<CommentResponseDto> getCommentsByUserId(Long userId, Pageable pageable) {

        Slice<Comment> comments = commentRepository.findByUserId(userId, pageable);

        if(comments == null) {
            throw new CustomException(ExceptionCode.FIND_NOT_INTERFACE);
        }

        return comments.map(CommentResponseDto::new);
    }

    /**
     * 특정 게시글에 달린 댓글을 수정합니다.
     * @param commentId
     * @param userId
     * @param requestDto
     * @return
     */

    @Transactional
    public CommentResponseDto updateComment(Long commentId, Long userId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.FIND_NOT_INTERFACE));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        comment.update(requestDto);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    /**
     * 특정 게시글에 달린 댓글을 삭제합니다.
     * @param userId
     * @param commentId
     * @return
     */

    @Transactional
    public void deleteComment(Long commentId, Long userId,Long postId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.FIND_NOT_INTERFACE));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ExceptionCode.FIND_NOT_INTERFACE));
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}