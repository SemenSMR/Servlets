package org.example.java.ru.netology.service;

import org.example.java.ru.netology.exception.NotFoundException;
import org.example.java.ru.netology.model.Post;
import org.example.java.ru.netology.repository.PostRepository;

import java.util.Collection;
import java.util.Optional;

public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public Collection<Post> all() {
    return repository.all();
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(repository.getById(id));
  }

  public Post save(Post post) {
    Post result = repository.save(post);
    if (result == null)
      throw new NotFoundException(
              String.format("Не удалось обновить запись с id = %d т.к. она отсутствует", post.getId()));
    return result;
  }

  public void removeById(long id) {
    repository.removeById(id);
  }
}

