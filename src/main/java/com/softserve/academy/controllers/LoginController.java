package com.softserve.academy.controllers;

import com.softserve.academy.models.Role;
import com.softserve.academy.models.User;
import com.softserve.academy.security.JwtUtility;
import com.softserve.academy.security.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private List<User> users = new ArrayList<>();
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginController(BCryptPasswordEncoder passwordEncoder) {
        this.bCryptPasswordEncoder = passwordEncoder;
        users.add(new User(1, "mike",
                bCryptPasswordEncoder.encode("1111"),
                Arrays.asList(new Role("ROLE_WRITER"), new Role("ROLE_READER"))));
        users.add(new User(2, "nick",
                bCryptPasswordEncoder.encode("2222"),
                Collections.singletonList(new Role("ROLE_READER"))));
    }

    @PostMapping("/login")
    public void loginProcessing(@RequestBody UserCredentials credentials, HttpServletResponse response) {

        User user = null;

        for (User item : users) {
            if (item.getUsername().equals(credentials.getUsername()) && bCryptPasswordEncoder.matches(credentials.getPassword(), item.getPassword())) {
                user = item;
            }
        }

        if (user != null) {
            String token = JwtUtility.createToken(user);
            response.addHeader("Authorization", "Bearer " + token);
        } else {
            throw new RuntimeException("Bad Credentials!");
        }

    }
}
