package com.xws111.sqlpractice.model.vo;

import lombok.Data;

/**
 * @Description:
 * @Date 2024/6/17 18:02
 * @Version 1.0
 * @Author xg
 */
@Data
public class QuestionListVO {
    private Integer id;
    private String title;
    private Integer favorNum;
    private Long submitNum;
    private Long accepted;
    private Integer difficulty;
}
