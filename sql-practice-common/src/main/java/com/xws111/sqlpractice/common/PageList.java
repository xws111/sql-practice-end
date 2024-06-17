package com.xws111.sqlpractice.common;

import lombok.Data;

import java.util.List;

@Data
public class PageList<T> {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 记录总数
     */
    private long total;
    /**
     * 分页数据
     */
    private List<T> content;

}
