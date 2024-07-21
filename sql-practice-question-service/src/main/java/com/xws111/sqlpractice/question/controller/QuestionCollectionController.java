package com.xws111.sqlpractice.question.controller;

import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.question.service.QuestionCollectionService;
import com.xws111.sqlpractice.service.UserFeignClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户收藏题目相关类
 */
@RestController
@RequestMapping("/collection")
@Slf4j
public class QuestionCollectionController {

    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private QuestionCollectionService questionCollectionService;

    /**
     * 用户收藏题目接口
     * @param questionId
     * @param request
     * @return
     */
    @ApiOperation(value = "用户收藏题目接口",notes = "用户收藏题目接口")
    @PostMapping("/add")
    public BaseResponse<Boolean> questionCollect(@RequestParam Long questionId, HttpServletRequest request){
        // 登录后才能收藏
        final User loginUser = userFeignClient.getLoginUser(request);
        if(loginUser == null){
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR,"用户未登录");
        }
        boolean res = questionCollectionService.doQuestionCollection(questionId, loginUser);
        return ResultUtils.success(res);
    }


    @ApiOperation(value = "用户取消收藏题目接口",notes = "用户取消收藏题目接口")
    @PostMapping("/cancel")
    public BaseResponse<Boolean> questionCancelCollect(@RequestParam Long questionId, HttpServletRequest request){
        final User loginUser = userFeignClient.getLoginUser(request);
        boolean res = questionCollectionService.cancelQuestionCollection(questionId, loginUser);
        return ResultUtils.success(res);
    }
}
