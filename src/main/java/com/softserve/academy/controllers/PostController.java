package com.softserve.academy.controllers;

import com.softserve.academy.models.Post;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private List<Post> posts = new ArrayList<>();

    public PostController() {
        posts.add(new Post(1, "First Mike's post", new Date(), 1));
        posts.add(new Post(2, "First Nick's post", new Date(), 2));
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated() and hasRole('WRITER')")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @GetMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("(hasRole('WRITER') or hasRole('READER')) and returnObject.userId == authentication.details.id")
    public Post read(@PathVariable int id) {
        for (Post post : posts) {
            if (post.getId() == id) return post;
        }
        throw new RuntimeException("Post not found");
    }

    @DeleteMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated() and " +
            "(hasRole('WRITER') or hasRole('READER')) and #post.userId == authentication.details.id")
    public void delete(@RequestBody Post post) {
        for (Post item : posts) {
            if (item.equals(post))
                posts.remove(post);
        }
        throw new RuntimeException("Post not found");
    }

}
