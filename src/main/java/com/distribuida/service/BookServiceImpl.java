package com.distribuida.service;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class BookServiceImpl implements IBookService{

    @Inject
    EntityManager entity;

    @Override
    public Book buscarPorId(Integer id) {
        return entity.find(Book.class,id);
    }

    @Override
    public List<Book> buscarTodos() {
        return entity.createQuery("select b from Book b ", Book.class).getResultList();
    }

    @Override
    public void agregar(Book b) {
        entity.getTransaction().begin();
        entity.persist(b);
        entity.getTransaction().commit();
    }

    @Override
    public void actualizar(Book b) {
        entity.getTransaction().begin();
        entity.merge(b);
        entity.getTransaction().commit();
    }

    @Override
    public void eliminar(Integer id) {
        Book libro = this.buscarPorId(id);
        entity.getTransaction().begin();
        entity.remove(libro);
        entity.getTransaction().commit();
    }
}