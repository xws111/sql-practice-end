package com.xws111.sqlpractice.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 提交id
     */
    private Long id;
    /**
     * 判题结果
     */
    private String result;
    /**
     * 查询结果
     */
    private String queryResult;

    /**
     * 消耗时间（s）
     */
    private long time;

}
