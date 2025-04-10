package org.example.newsfeedteamproject.commentLikes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 좋아요 처리에 대한 응답 정보를 담는 DTO 클래스입니다.
 *
 * 클라이언트에게 좋아요 처리 결과와 해당 댓글의 총 좋아요 수를 전달합니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeResponseDto {

    /**
     * 좋아요 처리 성공 여부를 나타냅니다.
     * true이면 성공, false이면 실패입니다.
     */
    private boolean success;

    /**
     * 해당 댓글의 총 좋아요 수를 나타냅니다.
     */
    private int commentLikeCount;
}
