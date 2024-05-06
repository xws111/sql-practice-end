package com.xws111.sqlpractice.model.vo;



import com.xws111.sqlpractice.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 单个题目
 * @TableName question
 */
@Data
public class QuestionVO implements Serializable {
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
     * 题目提交数
     */
    private Integer submitNum;

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


    private static final long serialVersionUID = 1L;
}