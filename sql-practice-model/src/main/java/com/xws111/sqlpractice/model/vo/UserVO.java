package com.xws111.sqlpractice.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Date 2024/5/5 14:17
 * @Version 1.0
 * @Author xg
 */
@Data
public class UserVO implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 用户昵称
     */ 
    private String username;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;
    /**
     * 创建时间
     */
    private Date createTime;
    private static final long serialVersionUID = 1L;
}
