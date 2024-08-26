package com.xws111.sqlpractice.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 文章列表界面 VO 类
 */

@Data
public class ArticleListVO {
    private Integer id;
    private String title;
    private String description;
    private String authorName;
    private Date createTime;
    private Date updateTime;
    private String cover;
    private Integer likes;
}
