package com.xws111.sqlpractice.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 标签表
 * @TableName tag
 */
@TableName(value ="tag")
@Data
public class Tag implements Serializable {
    /**
     * 标签id
     */
    @TableId(type = IdType.AUTO, value = "tag_id")
    private Integer id;

    /**
     * 标签名称
     */
    @TableField(value = "tag_name")
    private String name;

    /**
     * 创建时间
     */
    @TableField(value = "tag_create_time")
    private Date createTime;

    /**
     * 0正常，1删除
     */
    @TableField(value = "tag_deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}