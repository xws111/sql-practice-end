package com.xws111.sqlpractice.model.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {
    private Integer id;
    private String name;
    private String author;
    private String description;
    private BigDecimal price;
    private String cover;
    private Integer pages;
    private Integer categoryId;
    private String keyword;
}
