package com.xws111.sqlpractice.model.vo;



import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 单个题目
 * @TableName question
 */
@Data
public class QuestionAllVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 答案
     */
    private String answer;
    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 收藏数
     */
    private Integer favorNum;

    /**
     * 题目通过数
     */
    private Integer accepted;

    /**
     * 时间限制
     */
    private Integer timeLimit;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 标签列表字符串
     */
    private String tagList;

    private static final long serialVersionUID = 1L;
}