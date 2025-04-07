package org.example.newsfeedteamproject.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.comment.dto.CommentRequestDto;
import org.example.newsfeedteamproject.comment.dto.CommentResponseDto;
import org.example.newsfeedteamproject.comment.entity.Comment;
import org.example.newsfeedteamproject.comment.repository.CommentRepository;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.post.repository.PostRepository;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto addComment(Long userId, Long postId, CommentRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Comment comment = new Comment(post, user, requestDto.getContents());
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
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

        comment.update(requestDto.getName(), requestDto.getContents());
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