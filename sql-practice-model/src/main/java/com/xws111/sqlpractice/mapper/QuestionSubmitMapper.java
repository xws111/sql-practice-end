package com.xws111.sqlpractice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.vo.QuestionSubmitResultVO;
import com.xws111.sqlpractice.model.vo.QuestionSubmitVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author xg
* @description 针对表【question_submit(提交记录表)】的数据库操作Mapper
* @createDate 2024-05-06 18:28:36
* @Entity generator.domain.QuestionSubmit
*/
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {
    @Select("SELECT language,code,result,status,output from question_submit q" +
            " where id = #{user_id} and question_id = #{question_id} order by create_time desc")
    QuestionSubmitResultVO getQuestionSubmitVOById(@Param("user_id") Integer userId, @Param("question_id") Integer questionId);

}




