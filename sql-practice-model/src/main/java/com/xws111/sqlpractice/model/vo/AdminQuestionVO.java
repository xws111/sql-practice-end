package com.xws111.sqlpractice.model.vo;

import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.model.entity.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description:
 * @Date 2024/6/17 13:23
 * @Version 1.0
 * @Author xg
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminQuestionVO extends Question {
    List<String> tags;
}
