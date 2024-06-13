package com.distribuida.service;

import com.distribuida.db.Book;

import java.util.List;


public interface IBookService {

    //C
    void agregar(Book libro);
    //R
    Book buscarPorId(Integer id);
    List<Book> buscarTodos();
    //U
    void actualizar(Book libro);
    //D
    void eliminar(Integer id);

}