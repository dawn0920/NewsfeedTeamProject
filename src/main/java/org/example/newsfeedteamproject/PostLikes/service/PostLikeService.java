package org.example.newsfeedteamproject.PostLikes.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.PostLikes.dto.PostLikeResponseDto;
import org.example.newsfeedteamproject.PostLikes.entity.PostLikes;
import org.example.newsfeedteamproject.PostLikes.repository.PostLikeRepository;
import org.example.newsfeedteamproject.global.error.CustomException;
import org.example.newsfeedteamproject.global.error.ExceptionCode;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.post.repository.PostRepository;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 좋아요 토글(눌렀을때 좋아요 한번 더 누르면 취소)
    @Transactional
    public PostLikeResponseDto toggleLike(User fromUser, Long postId) {
        // postId 조회
        Post toPost = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ExceptionCode.POST_NOT_FOUND));

        // 이미 눌려있는지 확인
        Optional<PostLikes> existiongLiked = postLikeRepository.findByFromUserAndToPost(fromUser, toPost);

        if (existiongLiked.isPresent()) {
            // 좋아요가 되어있으면 취소
            postLikeRepository.delete(existiongLiked.get());

            toPost.decreaseLike();
            postRepository.save(toPost);

            int currentLikeCount = postLikeRepository.countByToPost(toPost);
            return new PostLikeResponseDto(false, currentLikeCount);
        } else {
            // 좋아요
            postLikeRepository.save(new PostLikes(fromUser, toPost));

            toPost.increaseLike();
            postRepository.save(toPost);

            int currentLikeCount = postLikeRepository.countByToPost(toPost);
            return new PostLikeResponseDto(true, currentLikeCount);
        }
    }
}
