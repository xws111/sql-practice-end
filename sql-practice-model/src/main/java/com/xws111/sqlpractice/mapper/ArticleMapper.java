package com.xws111.sqlpractice.mapper;


import com.xws111.sqlpractice.model.vo.ArticleListVO;
import com.xws111.sqlpractice.model.vo.ArticleRecommendListVO;
import com.xws111.sqlpractice.model.vo.ArticleVO;
import com.xws111.sqlpractice.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<ArticleListVO> selectArticlesByPage();

    List<String> selectCategoryNamesByArticleId(Integer articleId);

    List<ArticleListVO> selectArticlesByCategories(String category);

    @Select("SELECT a.id, a.title, u.username as authorName, a.content, a.create_time, a.update_time, a.cover as coverUrl, a.likes, a.views " +
            "FROM article a " +
            "LEFT JOIN user u " +
            "ON u.id = a.author_id " +
            " WHERE a.id = #{articleId} ")
    ArticleVO selectArticleById(Integer id);
    @Select("SELECT a.id, a.title, a.views " +
            "FROM article a " +
            "ORDER BY views " +
            "LIMIT 10")
    List<ArticleRecommendListVO> getRecommendArticle();

    @Select("SELECT u.id, u.username, u.avatar_url as avatar, u.role as userRole, u.create_time " +
            "FROM user u " +
            "LEFT JOIN article a " +
            "ON u.id = a.author_id " +
            "WHERE a.id = #{id} ")
    UserVO getAuthor(Integer id);
}
