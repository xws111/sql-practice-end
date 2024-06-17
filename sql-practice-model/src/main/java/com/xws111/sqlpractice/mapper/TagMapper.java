package com.xws111.sqlpractice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xws111.sqlpractice.model.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author xg
* @description 针对表【tag(标签表)】的数据库操作Mapper
* @createDate 2024-05-06 18:28:36
* @Entity generator.domain.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {
    @Select("SELECT t.name " +
            "FROM tag t " +
            "LEFT JOIN question_tag qt ON t.id = qt.tag_id " +
            "WHERE qt.question_id = #{questionId}")
    List<String> getTagNamesByQuestionId(@Param("questionId") Long id);

    /**
     * 取出指定问题的标签
     * @param id
     * @return
     */
    List<String> getTagsByQuestionId(@Param("id") Long id);
}




