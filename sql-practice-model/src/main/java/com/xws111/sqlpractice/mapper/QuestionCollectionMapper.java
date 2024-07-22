package com.xws111.sqlpractice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xws111.sqlpractice.model.entity.QuestionCollection;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author wind
 * @description 针对表【question_collection(题目收藏表)】的数据库操作Mapper
 * @createDate 2024-07-21 14:04:06
 */
public interface QuestionCollectionMapper extends BaseMapper<QuestionCollection> {

    /**
     * 获取用户收藏的题目列表
     * @param userId
     * @return
     */
    List<QuestionListVO> getQuestionCollectionList(@Param("userId") Long userId);

    /**
     * 查询用户是否曾经收藏过该题目
     * @param userId
     * @param QuestionId
     * @return
     */
    @Select("select * from question_collection where user_id = #{userId} and question_id = #{questionId}")
    QuestionCollection queryQuestionCollection(@Param("userId") Long userId,@Param("questionId") Long QuestionId);

    /**
     * 取消后再收藏题目
     * @param userId
     * @param QuestionId
     * @return
     */
    @Update("update question_collection set is_deleted = 0 where user_id = #{userId} and question_id = #{questionId}")
    int reCollectQuestion(@Param("userId") Long userId,@Param("questionId") Long QuestionId);
}




