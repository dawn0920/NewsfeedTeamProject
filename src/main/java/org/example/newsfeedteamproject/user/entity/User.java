package org.example.newsfeedteamproject.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.newsfeedteamproject.base_entity.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor

public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String userRefId;

    @Column(nullable = true)
    private String intro;

    @Builder.Default
    @Column(nullable = false)
    private int follow = 0;

    @Builder.Default
    @Column(nullable = false)
    private int following = 0;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0" )
    private boolean withdrawn;

    @Column(nullable = true)
    private String fileName;

    @Column(nullable = true)
    private String filePath;


    public User() {
    }

    public User(String email, String password, String userRefId, String name, String birthday, String phone, String fileName, String filePath) {
        this.email = email;
        this.password = password;
        this.userRefId = userRefId;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.fileName = fileName;
        this.filePath = filePath;
    }


    public void update(String email, String userRefId, String name, String birthday, String phone){
        this.email = email;
        this.userRefId = userRefId;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
    }

    public Long getId() {
        return id;
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

    public void setFollowCountByListSize(int followerCount, int followingCount) {
        this.follow = followerCount;
        this.following = followingCount;
    }

    /**
     * 스프링 시큐리티, jwt토큰에 필요한 요소들입니다.
     */

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
