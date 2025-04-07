package org.example.newsfeedteamproject.post.repository;

import org.example.newsfeedteamproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository <Post,Long> {
}
