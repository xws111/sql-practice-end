package com.xws111.sqlpracticearticleservice.service;

import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.model.vo.ArticleListVO;

public interface ArticleService {
    PageInfo<ArticleListVO> getArticles(int pageNum, int pageSize);

    PageInfo<ArticleListVO> getArticleByCategory(int pageNum, int pageSize, String category);
}
