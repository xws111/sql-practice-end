package com.xws111.sqlpractice.model.vo;


import lombok.Data;

import java.util.Date;

@Data
public class QuestionSubmitVO {

    private Long id;

    /**
     * 提交语言
     */
    private String language;

    /**
     * 提交结果
     */
    private String result;
    /**
     * 得到输出
     */
    private String output;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 提交状态字符串
     */
    private String statusStr;

    /**
     * 题号名称
     */
    private String questionTitle;

    /**
     * 题号
     */
    private Long questionId;

    /**
     * 提交用户用户名
     */
    private String userName;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 运行时间
     */
    private Long duration;


}
