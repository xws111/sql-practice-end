package com.xws111.sqlpractice.question.controller;

import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;

import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.exception.ThrowUtils;
import com.xws111.sqlpractice.model.dto.question.*;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.model.vo.QuestionVO;
import com.xws111.sqlpractice.question.service.QuestionService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户获取题目信息接口
 */
@RestController
@RequestMapping("/")
@Api(tags = "用户获取题目信息接口")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    /**
     * 获取指定 id 题目详细信息接口
     *
     * @param id 题目id
     * @return 题目包装类
     */
    @ApiOperation(value = "获取指定题目信息接口", notes = "获取指定题目信息接口")
    @GetMapping("/{id}")
    public BaseResponse<QuestionVO> getQuestionVOById(@PathVariable Long id, HttpServletRequest request) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(questionService.getQuestionVOById(id, request));
    }


    /**
     * 分页获取题目列表
     *
     * @param questionListRequest 题目列表查询包装类
     * @return 分页题目列表包装类
     */
    @ApiOperation(value = "分页获取所有题目列表接口", notes = "分页获取所有题目列表接口")
    @GetMapping("/list/page")
    public BaseResponse<PageInfo<QuestionListVO>> listQuestionByPage(@ModelAttribute QuestionListRequest questionListRequest) {

        int current = questionListRequest.getCurrent();
        int size = questionListRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        PageInfo<QuestionListVO> page = questionService.getQuestionListPage(questionListRequest);
        return ResultUtils.success(page);
    }


}
