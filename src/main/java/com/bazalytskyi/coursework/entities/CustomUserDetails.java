package com.bazalytskyi.coursework.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;


public class CustomUserDetails implements UserDetails {
    private UserEntity user;

    public CustomUserDetails(UserEntity user){
        this.user = user;
    }

    public CustomUserDetails(long id, String subject, String grantedAuthorities) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setRole(new RoleEntity(grantedAuthorities));
        user.setUsername(subject);
        this.user=user;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isTokenExpired();
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isTokenExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override
    public String toString() {
        return this.getUsername();
    }
}
