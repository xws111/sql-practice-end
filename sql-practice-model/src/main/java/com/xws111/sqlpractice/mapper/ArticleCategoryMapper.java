package com.xws111.sqlpractice.mapper;

import com.xws111.sqlpractice.model.entity.Article;
import com.xws111.sqlpractice.model.entity.ArticleCategory;

import java.util.List;

public interface ArticleCategoryMapper {
    int insertArticleCategory(ArticleCategory articleCategory);
    int deleteByCompositeKey(Integer articleId);
    List<Article> selectArticlesByCategory(List<String> categoryNames);
}
