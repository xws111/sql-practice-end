package com.xws111.sqlpracticearticleservice.controller;

import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.model.entity.ArticleCategory;
import com.xws111.sqlpractice.model.vo.ArticleListVO;
import com.xws111.sqlpracticearticleservice.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
