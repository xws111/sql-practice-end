package com.xws111.sqlpractice.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 提交记录表
 * @TableName question_submit
 */
@TableName(value ="question_submit")
@Data
public class QuestionSubmit implements Serializable {
    /**
     * 记录 id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 提交语言
     */
    private String language;

    /**
     * 提交代码
     */
    private String code;

    /**
     * 提交结果
     */
    private String result;

    /**
     * 提交状态 
     */
    private Integer status;

    /**
     * 题号
     */
    private Long questionId;

    /**
     * 提交用户id
     */
    private Long userId;

    /**
     * 提交时间
     */
    private Date createTime;

    /**
     * 0 - 正常 1 - 删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}