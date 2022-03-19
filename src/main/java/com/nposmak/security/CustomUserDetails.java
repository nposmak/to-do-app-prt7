package com.nposmak.security;


import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nposmak.entity.Role;
import com.nposmak.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    public boolean hasRole(String roleName) {
        return this.user.hasRole(roleName);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
