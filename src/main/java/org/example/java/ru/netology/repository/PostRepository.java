package org.example.java.ru.netology.repository;

import org.example.java.ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public Collection<Post> all() {
        return posts.values();
    }

    public Post getById(long id) {
        return posts.get(id);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(nextId.getAndIncrement());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
       posts.remove(id);
    }
}
