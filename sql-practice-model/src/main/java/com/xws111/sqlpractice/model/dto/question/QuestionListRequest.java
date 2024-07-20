package com.xws111.sqlpractice.model.dto.question;

import com.xws111.sqlpractice.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionListRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

//    /**
//     * userId
//     */
//    private Long userId;
    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    List<String> tags;

    private static final long serialVersionUID = 1L;
}