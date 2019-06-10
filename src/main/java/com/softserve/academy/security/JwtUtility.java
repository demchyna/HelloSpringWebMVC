package com.softserve.academy.security;

import com.softserve.academy.models.Role;
import com.softserve.academy.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class JwtUtility {

    private static String TOKEN_PREFIX = "Bearer";
    private static long EXPIRATION_TIME = 300000;
    private static String SECRET = "MySecret";

    public static String createToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("id", user.getId())
                .claim("roles", user.getRoles())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return token;
    }

    public static String refreshToken(Authentication authentication) {
        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("roles", authentication.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return token;
    }

    public static int getUserIdFromToken(String token) {
        Number id = (Number) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().get("id");

        return id.intValue();
    }

    public static String getUsernameFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().getSubject();

        return username;
    }

    public static Collection<Role> getRolesFromToken(String token) {
        Collection roles = (List) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().get("roles");

        return roles;
    }

}
