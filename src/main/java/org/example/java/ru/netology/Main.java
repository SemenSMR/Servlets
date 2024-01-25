package org.example.java.ru.netology;

import org.example.java.ru.netology.controller.PostController;
import org.example.java.ru.netology.service.PostService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext("org.example");
        final var controller = context.getBean(PostController.class);
        final var service = context.getBean(PostService.class);
    }
}
