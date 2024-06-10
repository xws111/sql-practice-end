package com.xws111.sqlpractice.model.dto.user;

import lombok.Data;

/**
 * @author Fancier
 * @version 1.0
 * @description: 用户删除请求
 * @date 2024/6/10 11:29
 */
@Data
public class UserDeleteRequest {
    /**
     * 用户 id
     */
    private Long id;
}
