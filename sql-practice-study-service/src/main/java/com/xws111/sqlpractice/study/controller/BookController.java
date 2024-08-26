package com.xws111.sqlpractice.study.controller;


import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.model.entity.Book;
import com.xws111.sqlpractice.study.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("book")
public class BookController {

    @Resource
    BookService bookService;

    @GetMapping("/all")
    public BaseResponse<List<Book>> getAllBooks() {
        return ResultUtils.success(bookService.getAllBooks());
    }

    @GetMapping("/category")
    public BaseResponse<List<Book>> getAllBooksByCategory(@RequestParam String category) {
        return ResultUtils.success(bookService.getBooksByCategory(category));
    }
    @GetMapping("/keyword/{id}")
    public BaseResponse<String> getBookKeywordById(@PathVariable Integer id) {
        return ResultUtils.success(bookService.getBookKeyWord(id));
    }
}
