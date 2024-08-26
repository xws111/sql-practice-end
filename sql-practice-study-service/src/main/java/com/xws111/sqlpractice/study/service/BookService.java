package com.xws111.sqlpractice.study.service;

import com.xws111.sqlpractice.model.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    List<Book> getBooksByCategory(String category);

    String getBookKeyWord(Integer bookId);
}
