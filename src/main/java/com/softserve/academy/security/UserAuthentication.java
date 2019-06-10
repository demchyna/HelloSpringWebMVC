package com.softserve.academy.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    private String token;
    private UserDetails userDetails;
    private boolean authenticated;

    public UserAuthentication(String token, UserDetails userDetails) {
        this.token = token;
        this.userDetails = userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getRoles();
    }

    @Override
    public Object getCredentials() {
        return new UserCredentials(userDetails.getUsername(), userDetails.getPassword());
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }

    public String getToken() {
        return token;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
