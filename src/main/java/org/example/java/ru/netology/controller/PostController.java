package org.example.java.ru.netology.controller;

import com.google.gson.Gson;
import org.example.java.ru.netology.exception.NotFoundException;
import org.example.java.ru.netology.model.Post;
import org.example.java.ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;
  private final Gson gson = new Gson();

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
   sendResponse(response,service.all());
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    try {
      final var data = service.getById(id)
              .orElseThrow(() -> new NotFoundException(String.format("Запись с id=%d отсутствует", id)));
      sendResponse(response, data);
    } catch (NotFoundException e) {
      sendResponse(response, e.getMessage());
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    try {
      final var data = service.save(gson.fromJson(body, Post.class));
      sendResponse(response, data);
    } catch (NotFoundException e) {
      sendResponse(response, e.getMessage());
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  public void removeById(long id, HttpServletResponse response) {
    service.removeById(id);
  }
  private <T> void sendResponse(HttpServletResponse response, T data) throws IOException {
    response.setContentType(APPLICATION_JSON);
    response.getWriter().print(gson.toJson(data));
  }
}
