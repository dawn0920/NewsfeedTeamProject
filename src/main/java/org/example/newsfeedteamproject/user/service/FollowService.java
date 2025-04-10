package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.global.error.CustomException;
import org.example.newsfeedteamproject.global.error.ExceptionCode;
import org.example.newsfeedteamproject.user.dto.FollowResponseDto;
import org.example.newsfeedteamproject.user.entity.Follow;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.FollowRepository;
import org.example.newsfeedteamproject.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor // 클래스 내 final 혹은 @NonNull 이 붙은 필드만 포함하는 생성자
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    @Transactional
    public FollowResponseDto toggleFollowUser(User fromUser, Long toUserId) {
        // userId 찾기
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        // 이미 눌렀는지 확인
        // 엔티티 자체를 가져오기 떄문에 Optional findBy...() + Optional<Follow> 사용
        Optional<Follow> existingFollow = followRepository.findByFromUserAndToUser(fromUser, toUser);

        if (toUserId.equals(fromUser.getId()) ) {
            throw new IllegalArgumentException("자기 자신은 팔로우 할 수 없습니다.");
            // IllegalArgumentException -> 잘못된 인자 접근시 사용되는 런타임 예외
        }

        if (existingFollow.isPresent()) {
            // 팔로우 되어 있으면 언팔로우 처리
            followRepository.delete(existingFollow.get());

            fromUser.decreaseFollowing();
            toUser.decreaseFollower();

            userRepository.save(fromUser);
            userRepository.save(toUser);

            int currentFollowCount = followRepository.countByToUser(toUser);
            return new FollowResponseDto(false, currentFollowCount); // false = 언팔로우 상태

        } else {
            // 팔로우 안 되어 있으면 팔로우 처리
            followRepository.save(new Follow(toUser, fromUser));

            fromUser.increaseFollowing();
            toUser.increaseFollower();

            userRepository.save(fromUser);
            userRepository.save(toUser);

            int currentFollowCount = followRepository.countByToUser(toUser);
            return new FollowResponseDto(true, currentFollowCount); // true = 팔로우 상태
        }
    }

}
