package com.xws111.sqlpractice.model.vo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 题目详细信息 VO 类
 */
@Data
public class QuestionVO implements Serializable {
    /**
     * 题号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 题目
     */
    private String title;

    /**
     * 题目描述
     */
    private String content;

    /**
     * 时间限制 ms
     */
    private Integer timeLimit;

    /**
     * 收藏数
     */
    private Integer favorNum;

    /**
     * 提交数
     */
    private Long submitNum;

    /**
     * 通过数
     */
    private Long accepted;


    /**
     * 题目难度
     */
    private String difficulty;

    /**
     * 题目标签
     */
    private List<String> tags;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}