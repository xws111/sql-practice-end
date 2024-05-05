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
@TableName(value = "question_submit")
@Data
public class QuestionSubmit implements Serializable {
    /**
     * 记录 id
     */
    @TableId(type = IdType.AUTO, value = "question_submit_id")
    private Long id;

    /**
     * 提交语言
     */
    @TableField("question_submit_language")
    private String language;

    /**
     * 提交代码
     */
    @TableField("question_submit_code")
    private String code;

    /**
     * 提交结果
     */
    @TableField("question_submit_result")
    private String result;

    /**
     * 提交状态
     */
    @TableField("question_submit_status")
    private Integer status;

    /**
     * 题号
     */
    @TableField("question_submit_question_id")
    private Long questionId;

    /**
     * 提交用户id
     */
    @TableField("question_submit_user_id")
    private Long userId;

    /**
     * 提交时间
     */
    @TableField("question_submit_create_time")
    private Date createTime;

    /**
     * 0 - 正常 1 - 删除
     */
    @TableField("question_submit_deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
