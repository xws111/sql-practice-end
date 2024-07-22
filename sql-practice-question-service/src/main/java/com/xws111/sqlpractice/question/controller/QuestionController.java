package com.xws111.sqlpractice.question.controller;

import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;

import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.exception.ThrowUtils;
import com.xws111.sqlpractice.model.dto.question.*;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.model.vo.QuestionVO;
import com.xws111.sqlpractice.question.service.QuestionService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xws111.sqlpractice.service.UserFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户获取题目信息接口
 */
@RestController
@RequestMapping("/question")
@Api(tags = "用户获取题目信息接口")
@Slf4j
public class QuestionController {
    String USER_LOGIN_STATE = "USER_LOGIN_STATE";
    @Resource
    private QuestionService questionService;
    @Resource
    private UserFeignClient userFeignClient;

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
     * 分页获取特定用户题目列表 by noora
     * 2024/7/20
     *
     * @param questionListRequest 特定用户题目列表查询包装类
     * @return 分页特定用户题目列表包装类
     */
    @ApiOperation(value = "分页获取特定用户所有题目列表接口", notes = "分页获取特定用户所有题目列表接口")
    @GetMapping("/list/page")
    public BaseResponse<PageInfo<QuestionListVO>> userListQuestionByPage(@ModelAttribute QuestionListRequest questionListRequest, HttpServletRequest request) {

        int size = questionListRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        PageInfo<QuestionListVO> pages = questionService.getQuestionListPage(questionListRequest);
        // 如果用户没登陆，则和以前一样查询
        if(null == currentUser) {
            return ResultUtils.success(pages);
        }
        // 如果用户已经登录了，查询该用户的ac情况
        List<Integer> acList = questionService.getQuestionACList(currentUser.getId());
        // 遍历每页中的题目
        for(QuestionListVO page : pages.getList()) { // 确保你调用getList()来获取实际的列表
            if (acList.contains(page.getId())) {
                page.setStatus(true); // 设置题目为已通过
            } else {
                page.setStatus(false); // 设置题目为未通过
            }
        }
        return ResultUtils.success(pages);
    }

}
