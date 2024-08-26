package com.xws111.sqlpractice.model.vo;

import lombok.Data;


/**
 * 排行榜Vo类
 */

@Data
public class RankListVO {
    String username;
    String avatar;
    Integer accepted;
    Double duration;
}
