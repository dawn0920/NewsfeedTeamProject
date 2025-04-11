package org.example.newsfeedteamproject.post.repository;

import org.example.newsfeedteamproject.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * 게시글(Post) 엔티티에 대한 데이터 접근을 처리하는 JPA 리포지토리입니다.
 * Spring Data JPA를 통해 기본적인 CRUD 및 페이징, 정렬 기능을 제공합니다.
 */
@Repository
public interface PostRepository extends JpaRepository <Post, Long> {
    Slice<Post> findByUserId(Long userId, Pageable pageable);
}
