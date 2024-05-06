package com.xws111.sqlpractice.model.vo;



import com.xws111.sqlpractice.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 题目封装类
 * @TableName question
 */
@Data
public class QuestionListVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer accepted;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 包装类转对象
     *
     * @param questionListVO
     * @return
     */
    public static Question voToObj(QuestionListVO questionListVO) {
        if (questionListVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionListVO, question);
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionListVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionListVO questionListVO = new QuestionListVO();
        BeanUtils.copyProperties(question, questionListVO);
        return questionListVO;
    }

    private static final long serialVersionUID = 1L;
}