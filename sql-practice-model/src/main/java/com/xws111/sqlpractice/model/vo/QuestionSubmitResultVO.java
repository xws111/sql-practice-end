package com.xws111.sqlpractice.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionSubmitResultVO implements Serializable {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题结果
     */
    private String result;


    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;


    private String output;
}
