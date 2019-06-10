package com.softserve.academy.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.academy.models.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class WebAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserAuthentication userAuthentication = (UserAuthentication) authentication;

        try {
            userAuthentication.getUserDetails().setId(JwtUtility.getUserIdFromToken(userAuthentication.getToken()));
            userAuthentication.getUserDetails().setUsername(JwtUtility.getUsernameFromToken(userAuthentication.getToken()));

            Collection<Role> authorities = new ObjectMapper().convertValue(
                    JwtUtility.getRolesFromToken(userAuthentication.getToken()),
                    new TypeReference<Collection<Role>>() { }
            );

            userAuthentication.getUserDetails().setRoles(authorities);

            userAuthentication.setAuthenticated(true);

        } catch (SignatureException | ExpiredJwtException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return userAuthentication;
    }
}
