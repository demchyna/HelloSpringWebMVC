package com.softserve.academy.models;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private String authority;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.toUpperCase();
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
