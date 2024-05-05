package com.xws111.sqlpractice.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO, value = "user_id")
    private Long id;

    /**
     * 用户名
     */
    @TableField("user_username")
    private String username;

    /**
     * 账号
     */
    @TableField("user_account")
    private String account;

    /**
     * 密码
     */
    @TableField("user_password")
    private String password;

    /**
     * 头像地址
     */
    @TableField("user_avatar_url")
    private String avatarUrl;

    /**
     * 用户角色 0 - 普通用户  1 - 管理员
     */
    @TableField("user_role")
    private Integer role;

    /**
     * 创建时间
     */
    @TableField("user_create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("user_update_time")
    private Date updateTime;

    /**
     * 0 - 正常  1 - 删除
     */
    @TableField("user_deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}