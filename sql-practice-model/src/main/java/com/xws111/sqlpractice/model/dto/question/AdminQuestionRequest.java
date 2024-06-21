package com.xws111.sqlpractice.model.dto.question;

import com.xws111.sqlpractice.common.PageRequest;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Date 2024/6/17 12:24
 * @Version 1.0
 * @Author xg
 */
@Data
public class
AdminQuestionRequest {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 题目描述
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 时间限制
     */
    private Integer timeLimit;

    /**
     * 难度
     */
    private Integer difficulty;

    private static final long serialVersionUID = 1L;
}
