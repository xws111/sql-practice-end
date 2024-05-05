package com.xws111.sqlpractice.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableId(type = IdType.AUTO, value = "question_id")
    private Long id;

    /**
     * 题目
     */
    @TableField(value = "question_title")
    private String title;

    /**
     * 题目描述
     */
    @TableField(value = "question_description")
    private String description;

    /**
     * 答案
     */
    @TableField(value = "question_answser")
    private String answser;

    /**
     * 时间限制 s
     */
    @TableField(value = "question_time_limit")
    private Integer timeLimit;

    /**
     * 收藏数
     */
    @TableField(value = "question_favor_num")
    private Integer favorNum;

    /**
     * 提交数
     */
    @TableField(value = "question_submit_num")
    private Long submitNum;

    /**
     * 通过数
     */
    @TableField(value = "question_accepted")
    private Long accepted;

    /**
     * 创建时间
     */
    @TableField(value = "question_create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "question_update_time")
    private Date updateTime;

    /**
     * 0 正常   1 删除
     */
    @TableField(value = "question_deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}