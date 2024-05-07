package com.xws111.sqlpractice.model.dto.docker;

/**
 * @Description: 接收 response 的类
 * @Date 2024/5/7 16:14
 * @Version 1.0
 * @Author xg
 */
import lombok.Data;

@Data
public class ApiResponse {
    private Object outputList;  // 根据实际类型调整
    private String message;     // 这里为 null，可以根据实际用途调整类型
    private int status;
    private ExecuteResult judgeInfo;
}