package org.example.newsfeedteamproject.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedteamproject.base_entity.BaseEntity;


@Getter
@Entity
@Table(name = "`user`")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String userRefId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String intro;

    @Column(nullable = false)
    private String profileImg;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private int follow = 0;

    @Column(nullable = false)
    private int following = 0;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0" )
    private boolean withdrawn;

    public User() {
    }

    public User(String email, String password, String userRefId, String name, String intro, String profileImg, String birthday, String phone) {
        this.email = email;
        this.password = password;
        this.userRefId = userRefId;
        this.name = name;
        this.intro = intro;
        this.profileImg = profileImg;
        this.birthday = birthday;
        this.phone = phone;
    }

    public void update(String email, String userRefId, String name, String intro, String profileImg, String birthday, String phone){
        this.email = email;
        this.userRefId = userRefId;
        this.name = name;
        this.intro = intro;
        this.profileImg = profileImg;
        this.birthday = birthday;
        this.phone = phone;
    }

    public void withdrawn(boolean withdrawn){
        this.withdrawn = withdrawn;
    }

    public void increaseFollower() {
        this.follow++;
    }

    public void decreaseFollower() {
        this.follow = Math.max(0, this.follow - 1); // 0 이하로 내려가지 않게
    }

    public void increaseFollowing() {
        this.following++;
    }

    public void decreaseFollowing() {
        this.following = Math.max(0, this.following - 1);
    }
}
