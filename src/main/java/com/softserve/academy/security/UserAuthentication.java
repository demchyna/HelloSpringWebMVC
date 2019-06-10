package com.softserve.academy.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    private String token;
    private int userId;
    private String userName;
    private Collection<? extends GrantedAuthority> userRoles;
    private boolean authenticated;

    public UserAuthentication(String token) {
        this.token = token;
    }

    public void setUserRoles(Collection<? extends GrantedAuthority> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public Object getDetails() {
        return userId;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getName() {
        return userName;
    }

    public String getToken() {
        return token;
    }
}
