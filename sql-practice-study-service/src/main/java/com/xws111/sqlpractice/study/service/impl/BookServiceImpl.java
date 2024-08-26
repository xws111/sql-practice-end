package com.xws111.sqlpractice.study.service.impl;

import com.xws111.sqlpractice.mapper.BookMapper;
import com.xws111.sqlpractice.model.entity.Book;
import com.xws111.sqlpractice.study.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Resource
    BookMapper bookMapper;


    @Override
    public List<Book> getAllBooks() {
        return bookMapper.selectAll();
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return bookMapper.getBooksByCategory(category);
    }

    @Override
    public String getBookKeyWord(Integer bookId) {
        return bookMapper.getBookKeyWord(bookId);
    }
}
