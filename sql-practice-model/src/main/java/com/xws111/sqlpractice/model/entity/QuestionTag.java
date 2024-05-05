package com.xws111.sqlpractice.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 问题-标签关系表
 * @TableName question_tag
 */
@TableName(value ="question_tag")
@Data
public class QuestionTag implements Serializable {
    /**
     * 问题id
     */
    @TableId
    private Long questionId;

    /**
     * 标签id
     */
    private Integer tagId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}