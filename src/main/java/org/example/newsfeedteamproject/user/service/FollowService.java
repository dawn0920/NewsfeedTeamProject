package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.FollowResponseDto;
import org.example.newsfeedteamproject.user.entity.Follow;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.FollowRepository;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor // 클래스 내 final 혹은 @NonNull 이 붙은 필드만 포함하는 생성자
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우
    @Transactional
    public FollowResponseDto follwoUser(User fromUser, Long toUserId) {
        // touserid 가져오기
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        // 중복 확인(boolean)
        boolean alreadyFollowed = followRepository.existsByFromUserAndToUser(fromUser, toUser);
        if (alreadyFollowed) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "이미 팔로우한 유저입니다.");
        }

        // save
        followRepository.save(new Follow(toUser, fromUser));

        // 팔로우수 count
        int currentFollowCount = followRepository.countByToUser(toUser);
        return new FollowResponseDto(true, currentFollowCount);
    }

    // 언팔로우
    @Transactional
    public FollowResponseDto unfollwoUser(User fromUser, Long toUserId) {
        // touserid 가져오기
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        // 중복 확인(boolean)
        boolean alreadyFollowed = followRepository.existsByFromUserAndToUser(fromUser, toUser);
        if (!alreadyFollowed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "팔로우한 적이 없습니다.");
        }

        // delete
        followRepository.deleteByFromUserAndToUser(fromUser, toUser);

        // 팔로우수 count
        int currentFollowCount = followRepository.countByToUser(toUser);

        return new FollowResponseDto(false, currentFollowCount);
    }

}
