package org.example.newsfeedteamproject.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.comment.entity.CommentFactory;
import org.example.newsfeedteamproject.comment.repository.CommentRepository;
import org.example.newsfeedteamproject.commentLikes.entity.CommentLikes;
import org.example.newsfeedteamproject.global.error.CustomException;
import org.example.newsfeedteamproject.global.error.ExceptionCode;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.post.repository.PostRepository;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

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

        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ExceptionCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

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
            throw new CustomException(ExceptionCode.COMMENT_NOT_FOUND);
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
    public CommentResponseDto updateComment(Long userId, Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ExceptionCode.NO_EDIT_PERMISSION);
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
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ExceptionCode.NO_DELETE_PERMISSION);
        }

        commentRepository.delete(comment);
    }


    /**
     * 댓글 전체 조회
     */

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllCommentService() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
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

        if(comments.isEmpty()) {
            throw new CustomException(ExceptionCode.COMMENT_NOT_FOUND);
        }

        return comments.map(CommentResponseDto::new);
    }

}