package com.xws111.sqlpracticearticleservice.controller;

import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.model.entity.Article;
import com.xws111.sqlpractice.model.entity.ArticleCategory;
import com.xws111.sqlpractice.model.vo.ArticleListVO;
import com.xws111.sqlpractice.model.vo.ArticleRecommendListVO;
import com.xws111.sqlpractice.model.vo.ArticleVO;
import com.xws111.sqlpractice.model.vo.UserVO;
import com.xws111.sqlpracticearticleservice.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    // 分页查询所有文章
    @GetMapping("/page")
    public BaseResponse<PageInfo<ArticleListVO>> getArticleByPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        return ResultUtils.success(articleService.getArticles(page, size));
    }
    // 按照分类检索文章
    @GetMapping("/page/category")
    public BaseResponse<PageInfo<ArticleListVO>> getArticleByCategory(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size, @RequestParam String category) {
        return ResultUtils.success(articleService.getArticleByCategory(page, size, category));
    }

    // 根据获取文章详情
    @GetMapping("/{id}")
    public BaseResponse<ArticleVO> getArticleById(@PathVariable("id") Integer id) {
        return ResultUtils.success(articleService.getArticleById(id));
    }

    // 推荐文章
    @GetMapping("/recommend")
    public BaseResponse<List<ArticleRecommendListVO>> getRecommendArticle() {
        return ResultUtils.success(articleService.getRecommendArticle());
    }
    // 根据文章 id 获取作者信息
    @GetMapping("/author")
    public BaseResponse<UserVO> getAuthor(@RequestParam("articleId") Integer articleId) {
        return ResultUtils.success(articleService.getAuthor(articleId));
    }

}
