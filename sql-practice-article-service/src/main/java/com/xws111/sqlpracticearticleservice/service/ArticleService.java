package com.xws111.sqlpracticearticleservice.service;

import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.model.vo.ArticleListVO;
import com.xws111.sqlpractice.model.vo.ArticleRecommendListVO;
import com.xws111.sqlpractice.model.vo.ArticleVO;
import com.xws111.sqlpractice.model.vo.UserVO;

import java.util.List;

public interface ArticleService {
    PageInfo<ArticleListVO> getArticles(int pageNum, int pageSize);

    PageInfo<ArticleListVO> getArticleByCategory(int pageNum, int pageSize, String category);

    ArticleVO getArticleById(Integer id);

    List<ArticleRecommendListVO> getRecommendArticle();

    UserVO getAuthor(Integer id);
}
