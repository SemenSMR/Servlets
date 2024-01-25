package org.example.java.ru.netology.Config;

import org.example.java.ru.netology.controller.PostController;
import org.example.java.ru.netology.repository.PostRepository;
import org.example.java.ru.netology.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {

    @Bean
    public PostController postController() {
        return new PostController(postService());
    }
    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }
}
