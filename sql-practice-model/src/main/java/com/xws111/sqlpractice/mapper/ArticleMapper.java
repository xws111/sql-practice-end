package com.xws111.sqlpractice.mapper;


import com.xws111.sqlpractice.model.vo.ArticleListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<ArticleListVO> selectArticlesByPage();
    List<String> selectCategoryNamesByArticleId(Integer articleId);
    List<ArticleListVO> selectArticlesByCategories(String category);
}
