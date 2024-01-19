package org.example.java.ru.netology.repository;

import org.example.java.ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Stub
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();
    private long nextId = 1;

    public List<Post> all() {
        return new ArrayList<>(posts);
    }

    public Optional<Post> getById(long id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst();
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(nextId++);
            posts.add(post);

        } else {
            int index = findIndexById(post.getId()); {
                if (index != -1) {
                    posts.set(index, post);

                }
            }
        } return post;
    }


    public void removeById(long id) {
        int index = findIndexById(id);
        if (index != -1) {
            posts.remove(index);
        }
    }

    private int findIndexById(long id) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId() == id) {
                return i;
            }
        } return -1;
    }
}
