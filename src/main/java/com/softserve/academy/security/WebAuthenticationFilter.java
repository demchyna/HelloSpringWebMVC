package com.softserve.academy.security;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private Logger logger;

    public WebAuthenticationFilter(@Value("/api/**") String filterProcessesUrl) {
        super(new AntPathRequestMatcher(filterProcessesUrl));
    }

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            Authentication authentication = new UserAuthentication(token, new UserDetails());
            return getAuthenticationManager().authenticate(authentication);
        } else {
            try {
                throw new BadCredentialsException("Token is not found");
            } catch (BadCredentialsException exception) {
                logger.error(exception.getMessage(), exception);
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        response.setHeader("Authorization", "Bearer " + JwtUtility.refreshToken(authResult));
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        SecurityContextHolder.clearContext();
        logger.error(exception.getMessage(), exception);
    }
}
