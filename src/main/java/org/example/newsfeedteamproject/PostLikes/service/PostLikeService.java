package org.example.newsfeedteamproject.PostLikes.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.PostLikes.dto.PostLikeResponseDto;
import org.example.newsfeedteamproject.PostLikes.entity.PostLikes;
import org.example.newsfeedteamproject.PostLikes.repository.PostLikeReopsitory;
import org.example.newsfeedteamproject.post.entity.Post;
import org.example.newsfeedteamproject.post.repository.PostRepository;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeReopsitory postLikeReopsitory;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 좋아요 토글(눌렀을때 좋아요 한번 더 누르면 취소)
    @Transactional
    public PostLikeResponseDto toggleLike(User fromUser, Long postId) {
        // postId 조회
        Post toPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        // 이미 눌려있는지 확인
        boolean alreadyLiked = postLikeReopsitory.existsByFromUserAndToPost(fromUser, toPost);

        if (alreadyLiked) {
            // 좋아요 취소
            postLikeReopsitory.deleteByFromUserAndToPost(fromUser, toPost);
        } else {
            // 좋아요
            postLikeReopsitory.save(new PostLikes(fromUser, toPost));
        }

        int likeCount = postLikeReopsitory.countByToPost(toPost);
        return new PostLikeResponseDto(!alreadyLiked, likeCount);
    }
}
