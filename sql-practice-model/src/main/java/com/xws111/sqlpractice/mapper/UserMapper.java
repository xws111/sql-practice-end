package com.xws111.sqlpractice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.RankListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author xg
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-05-06 18:28:36
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT u.username, u.avatar_url as avatar, COUNT(DISTINCT qs.question_id) as accepted " +
            "FROM user u " +
            "LEFT JOIN question_submit qs ON u.id = qs.user_id " +
            "WHERE qs.status = 2 " +
            "GROUP BY u.id " +
            "ORDER BY accepted DESC " +
            "LIMIT 10")
    List<RankListVO> getTopUsersWithPassedCount();
    @Select("SELECT u.username,u.avatar_url, min(qs.duration) as duration\n" +
            "FROM user u\n" +
            "         LEFT JOIN question_submit qs ON u.id = qs.user_id\n" +
            "WHERE qs.question_id = #{questionId} AND qs.duration != 0\n" +
            "group by u.id\n" +
            "ORDER BY duration ASC\n" +
            "LIMIT #{size};\n")
    List<RankListVO> getUsersWithMinDuration(@Param("questionId") int questionId, @Param("size") int size);
}




