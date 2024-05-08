package com.xws111.sqlpractice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.vo.QuestionTagVO;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.model.vo.QuestionVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
* @author xg
* @description 针对表【question(题目表)】的数据库操作Mapper
* @createDate 2024-05-06 18:28:36
* @Entity generator.domain.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {


    @Select("SELECT q.id as id, q.title as title, q.submit_num as submitNum, q.accepted as accepted " +
            "FROM question q")
    List<QuestionListVO> getAllQuestions();

    @Select("SELECT qt.question_id as questionId, t.name as tagName " +
            "FROM question_tag qt " +
            "LEFT JOIN tag t ON t.id = qt.tag_id")
    List<QuestionTagVO> getAllQuestionTags();

    @Select("SELECT q.id, q.title, q.content, q.time_limit, q.submit_num as submitNum, q.accepted " +
        "FROM question q " +
        "WHERE q.id = #{id}"
    )
    QuestionVO getQuestionContent(@Param("id") Long id);

    @Select("SELECT question.answer FROM question WHERE id = #{id}")
    String getQuestionAnswerById(@Param("id") Long id);

    @Update("UPDATE question SET submit_num = question.submit_num + 1 WHERE id = #{questionId}")
    void incrementSubmitCount(@Param("questionId") Long questionId);

    @Update("UPDATE question SET accepted = question.accepted + 1 WHERE id = #{questionId}")
    void incrementAcceptedCount(@Param("questionId") Long questionId);

}




