package com.xws111.sqlpractice.mapper;


import com.xws111.sqlpractice.model.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> selectAll();

    List<Book> getBooksByCategory(String category);

    String getBookKeyWord(Integer bookId);
}
