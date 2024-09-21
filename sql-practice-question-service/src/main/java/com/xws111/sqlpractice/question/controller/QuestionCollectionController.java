package com.xws111.sqlpractice.question.controller;

import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.question.service.QuestionCollectionService;
import com.xws111.sqlpractice.security.annotation.LoginCheck;
import com.xws111.sqlpractice.service.UserFeignClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
     * @param question
     * @param request
     * @return
     */
    @ApiOperation(value = "用户收藏题目接口",notes = "用户收藏题目接口")
    @PostMapping("/add")
    @LoginCheck
    public BaseResponse<Boolean> questionCollect(@RequestBody Map<String, Long> question, HttpServletRequest request){
        Long questionId = question.get("questionId");
        // 登录后才能收藏
        final User loginUser = userFeignClient.getLoginUser(request);
        boolean res = questionCollectionService.doQuestionCollection(questionId, loginUser);
        return ResultUtils.success(res);
    }

    /**
     * 用户取消收藏题目接口
     * @param question
     * @param request
     * @return
     */
    @ApiOperation(value = "用户取消收藏题目接口",notes = "用户取消收藏题目接口")
    @PostMapping("/cancel")
    @LoginCheck
    public BaseResponse<Boolean> questionCancelCollect(@RequestBody Map<String, Long> question, HttpServletRequest request){
        final User loginUser = userFeignClient.getLoginUser(request);
        Long questionId = question.get("questionId");
        boolean res = questionCollectionService.cancelQuestionCollection(questionId, loginUser);
        return ResultUtils.success(res);
    }

    /**
     * 用户查看收藏题目接口
     * @param request
     * @return
     */
    @ApiOperation(value = "用户查看收藏题目接口",notes = "用户查看收藏题目接口")
    @GetMapping("/list")
    @LoginCheck
    public BaseResponse<List<QuestionListVO>> questionList(HttpServletRequest request){
        final User loginUser = userFeignClient.getLoginUser(request);
        List<QuestionListVO> questionCollectionList = questionCollectionService.getQuestionCollectionList(loginUser);
        return ResultUtils.success(questionCollectionList);
    }
}

