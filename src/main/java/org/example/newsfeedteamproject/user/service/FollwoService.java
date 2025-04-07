package org.example.newsfeedteamproject.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedteamproject.user.dto.FollwoResponseDto;
import org.example.newsfeedteamproject.user.entity.Follwo;
import org.example.newsfeedteamproject.user.entity.User;
import org.example.newsfeedteamproject.user.repository.FollwoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.LoginException;

@Service
@RequiredArgsConstructor // 클래스 내 final 혹은 @NonNull 이 붙은 필드만 포함하는 생성자
public class FollwoService {
    private final FollwoRepository follwoRepository;
    private final UserReopsitory userReopsitory;

    public FollwoResponseDto follwoUser(User fromUserID, Long toUserId) {
        // touserid 가져오기
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        // 중복 확인(boolean)
        boolean alreadyFollowed = follwoRepository.existsByFromUserAndToUser(fromUserID, toUser) {
            if (alreadyFollowed) {
                throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "이미 팔로우한 유저입니다.");
            }
        }

        // save
        follwoRepository.save(new Follwo(toUserId, fromUserID));

        // 팔로우수 count
        int currentFollowCount = follwoRepository.countByToUser(toUser);
        return new FollwoResponseDto(true, currentFollowCount);
    }
}
