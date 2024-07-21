package com.xws111.sqlpractice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xws111.sqlpractice.model.entity.QuestionCollection;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wind
 * @description 针对表【question_collection(题目收藏表)】的数据库操作Mapper
 * @createDate 2024-07-21 14:04:06
 */
public interface QuestionCollectionMapper extends BaseMapper<QuestionCollection> {

    List<QuestionListVO> getQuestionCollectionList(@Param("userId") Long userId);
}




