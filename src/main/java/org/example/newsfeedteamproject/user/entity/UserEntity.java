package org.example.newsfeedteamproject.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.newsfeedteamproject.entity.BaseEntity;

@Getter
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String intro;

    @Column(nullable = false)
    private String profileImg;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private int follow;

    @Column(nullable = false)
    private int following;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private boolean isWithdrawn;
}
