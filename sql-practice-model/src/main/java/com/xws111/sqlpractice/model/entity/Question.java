package com.xws111.sqlpractice.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 题目表
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
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
     * 答案
     */
    private String answer;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 题目难度
     */
    private Integer difficulty;

    /**
     * 0 正常   1 删除
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}