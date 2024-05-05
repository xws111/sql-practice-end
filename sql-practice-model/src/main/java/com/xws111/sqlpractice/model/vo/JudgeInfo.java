package com.xws111.sqlpractice.model.vo;


import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {

    /**
     * 查询结果
     */
    private String message;

    /**
     * 消耗时间（s）
     */
    private long time;

    /**
     * 查询结果 json
     */
    private String jsonResult;
}
