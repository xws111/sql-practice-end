package com.xws111.sqlpractice.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleVO {
    private Integer id;
    private String title;
    private String authorName;
    private String content;
    private Date createTime;
    private Date updateTime;
    private String coverUrl;
    private Integer likes;
    private Integer views;
}
