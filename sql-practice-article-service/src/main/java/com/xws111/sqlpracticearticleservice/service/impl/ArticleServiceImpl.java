package com.xws111.sqlpracticearticleservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.mapper.ArticleMapper;
import com.xws111.sqlpractice.model.vo.ArticleListVO;
import com.xws111.sqlpracticearticleservice.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public PageInfo<ArticleListVO> getArticles(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleListVO> articleListVOList = articleMapper.selectArticlesByPage();
        return new PageInfo<>(articleListVOList);
    }

    @Override
    public PageInfo<ArticleListVO> getArticleByCategory(int pageNum, int pageSize, String category) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleListVO> articleListVOList = articleMapper.selectArticlesByCategories(category);
        return new PageInfo<>(articleListVOList);
    }
}
