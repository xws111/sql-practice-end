package com.xws111.sqlpractice.question.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.DeleteRequest;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.annotation.AuthCheck;
import com.xws111.sqlpractice.constant.UserConstant;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.exception.ThrowUtils;
import com.xws111.sqlpractice.model.dto.question.*;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.QuestionTag;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionSubmitVO;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.model.vo.QuestionVO;
import com.xws111.sqlpractice.question.service.QuestionService;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import com.xws111.sqlpractice.question.service.QuestionTagService;
import com.xws111.sqlpractice.question.service.TagService;
import com.xws111.sqlpractice.service.UserFeignClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 题目接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/")
@Api(tags = "题目接口")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private TagService tagService;
    @Resource
    private QuestionTagService questionTagService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    private final static Gson GSON = new Gson();


    /**
     * 获取指定题目信息接口
     *
     * @param id 题目id
     * @return 题目包装类
     */
    @ApiOperation(value = "getQuestionVOById", notes = "获取指定题目信息接口")
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
    @ApiOperation(value = "listQuestionByPage", notes = "分页获取题目列表接口")
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionListVO>> listQuestionByPage(@RequestBody QuestionListRequest questionListRequest) {

        long current = questionListRequest.getCurrent();
        long size = questionListRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionListVO> page = questionService.getQuestionList(current, size);
        return ResultUtils.success(page);
    }


}
