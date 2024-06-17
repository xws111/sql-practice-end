package com.xws111.sqlpractice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author xg
* @description 针对表【question(题目表)】的数据库操作Mapper
* @createDate 2024-05-06 18:28:36
* @Entity generator.domain.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 用户根据 id、标题 分页获取问题列表
     *
     * @param id
     * @param keyword

     * @return
     */
    List<QuestionListVO> getQuestionsVOList(@Param("id") Long id,
                                            @Param("keyword") String keyword);


    /**
     * 管理员获取问题列表
     * @param id
     * @param title
     * @param tags
     * @param difficulty
     * @return
     */
    List<Question> getQuestionsList(@Param("id") Long id,
                                    @Param("title") String title,
                                    @Param("tags") List<String> tags,
                                    @Param("difficulty") Integer difficulty);



    @Select("SELECT question.answer FROM question WHERE id = #{id}")
    String getQuestionAnswerById(@Param("id") Long id);

    @Update("UPDATE question SET submit_num = question.submit_num + 1 WHERE id = #{questionId}")
    void incrementSubmitCount(@Param("questionId") Long questionId);

    /**
     * 题目提交数 +1
     * @param questionId
     */
    @Update("UPDATE question SET accepted = question.accepted + 1 WHERE id = #{questionId}")
    void incrementAcceptedCount(@Param("questionId") Long questionId);

}




