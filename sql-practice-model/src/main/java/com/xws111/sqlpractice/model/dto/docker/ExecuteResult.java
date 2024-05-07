package com.xws111.sqlpractice.model.dto.docker;

import lombok.Data;

@Data
public class ExecuteResult {
    /**
     * 运行结果
     */
    private String message;
    /**
     * 时间消耗 ms
     */
    private long time;
    /**
     * json 查询结果
     */
    private String jsonResult;
}
