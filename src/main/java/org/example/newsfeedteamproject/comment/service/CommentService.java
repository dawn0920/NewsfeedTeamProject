package org.example.newsfeedteamproject.comment.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.comment.repository.CommentRepository;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.post.repository.PostRepository;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 등록 메소드
     * @param requestDto
     * @param session
     * @return
     */

    @Transactional
    public CommentResponseDto addComment(Long postId, CommentRequestDto requestDto, HttpSession session) {

        User loginUser = (User) session.getAttribute("User");

        if (loginUser == null) {
            throw new IllegalCallerException();
        }

        Post foundPost = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        Comment comment = new Comment(requestDto.getContents(), foundPost, loginUser);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatTimeDesc(postId);
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, Long userId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        comment.update(requestDto.getContents());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}