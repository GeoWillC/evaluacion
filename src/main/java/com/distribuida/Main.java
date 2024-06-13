package com.distribuida;

import com.distribuida.db.Book;
import com.distribuida.service.IBookService;
import com.google.gson.Gson;
import io.helidon.webserver.WebServer;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;
import java.util.List;

public class Main {
    private static ContainerLifecycle contexto = null;

    public static void main(String[] args) {
        contexto = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        contexto.startApplication(null);
        IBookService contenedor = CDI.current().select(IBookService.class).get();
        WebServer server = WebServer.builder()
                .port(8080)
                .routing(builder -> builder
                        .get("libros", (req, res) -> {
                            List<Book> libros = contenedor.buscarTodos();
                            res.send(new Gson().toJson(libros));
                        })
                        .get("libros/{id}", (req, res) -> {
                            Book libro = contenedor.buscarPorId(Integer.valueOf(req.path().pathParameters().get("id")));
                            res.send(new Gson().toJson(libro));
                        })
                        .post("libros", (req, res) -> {
                            String req_body = req.content().as(String.class);
                            Book libro = new Gson().fromJson(req_body, Book.class);
                            contenedor.agregar(libro);
                            res.send("Se ha insertado el libro con ISBN: " + libro.getIsbn());
                        })
                        .put("libros/{id}", (req, res) -> {
                            Gson gson = new Gson();
                            String req_body = req.content().as(String.class);
                            Book libro = gson.fromJson(req_body, Book.class);
                            Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
                            libro.setId(id);
                            contenedor.actualizar(libro);
                            res.send("Se ha actualizado el libro con id" + id);
                        })
                        .delete("libros/{id}", (req, res) -> {
                            Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
                            contenedor.eliminar(id);
                            res.send("Se borro el libro con id: " + id);
                        })
                )
                .build();
        server.start();
        contenedor.buscarTodos().forEach(libro -> System.out.println(libro));
        shutdown();
    }

    public static void shutdown() {
        contexto.stopApplication(null);
    }
}