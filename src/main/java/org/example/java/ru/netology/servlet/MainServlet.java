package org.example.java.ru.netology.servlet;

import org.example.java.ru.netology.controller.PostController;
import org.example.java.ru.netology.repository.PostRepository;
import org.example.java.ru.netology.service.PostService;
import org.example.java.ru.netology.handler.Handler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MainServlet extends HttpServlet {


    private PostController controller;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";
    private static final String API_PATH = "/api/posts";
    private final Map<String, Map<String, Handler>> handlers = new ConcurrentHashMap<>();


    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);

        addHandler(GET,API_PATH,((path, req, resp) -> controller.all(resp)));
        addHandler(GET,API_PATH,((path, req, resp) -> {
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/") +1));
            controller.getById(id,resp);
        }));
        addHandler(POST,API_PATH,((path, req, resp) -> controller.save(req.getReader(),resp)));
        addHandler(DELETE,API_PATH,((path, req, resp) -> {
            Long id = Long.parseLong(path.substring(path.lastIndexOf("/")+ 1));
            controller.removeById(id,resp);
        }));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
           Handler handler;
            if (path.startsWith(API_PATH) && path.matches(API_PATH+ "/\\d+")) {
                handler = handlers.get(method).get(API_PATH);
            } else {
                handler = handlers.get(method).get(path);
            }
            if (handler == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            handler.handle(path, req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    public void addHandler(String method, String path, Handler handler) {
        Map<String, Handler> map = new ConcurrentHashMap<>();
        if (handlers.containsKey(method)) {
            map = handlers.get(method);
        }
        map.put(path, handler);
        handlers.put(method, map);
    }

}

