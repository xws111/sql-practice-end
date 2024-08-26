package com.xws111.sqlpractice.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Long authorId;
    private Date createTime;
    private Date updateTime;
    private String cover;
    private Integer likes;
}
