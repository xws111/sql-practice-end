package com.xws111.sqlpractice.question.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.common.BaseResponse;

import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.exception.ThrowUtils;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.mapper.QuestionSubmitMapper;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import com.xws111.sqlpractice.service.UserFeignClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户提交代码相关类
 */
@RequestMapping("/submit")
@RestController
@Slf4j
public class QuestionSubmitController {
    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionSubmitMapper questionSubmitMapper;


    /**
     * 用户提交代码接口
     * @param request
     * @param questionSubmitAddRequest
     * @return
     */
    @ApiOperation(value = "用户提交代码接口",notes = "用户提交代码接口")
    @PostMapping("/add")
    public BaseResponse<Long> questionSubmit(HttpServletRequest request,
                                             @RequestBody @Valid QuestionSubmitAddRequest questionSubmitAddRequest) {
            // 登录才提交
            final User loginUser = userFeignClient.getLoginUser(request);

            long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
            return ResultUtils.success(questionSubmitId);
    }

    /**
     * 用户查询某个问题的提交历史记录，用于做题界面展示
     * @param request
     * @return
     */
    @ApiOperation(value = "用户查询某问题提交记录接口",notes = "用户查询某问题提交记录接口")
    @GetMapping("/result")
    public BaseResponse<List<QuestionSubmit>> questionSubmitResult(HttpServletRequest request, @RequestParam Long questionId){
        return ResultUtils.success(questionSubmitService.getQuestionSubmissionByQuestionId(request, questionId));
    }

    /**
     * 用户查询结果接口, 以不断访问此接口的形式让用户等待结果
     * @param questionId
     * @return
     */
    //TODO 不查询未成功的
    @ApiOperation(value = "用户查询结果接口",notes = "用户查询结果接口")
    @GetMapping("/{questionId}/submissions")
    public BaseResponse<QuestionSubmit> querySubmissionsByQuestionId (@PathVariable long questionId) {
        return ResultUtils.success(questionSubmitService.getById(questionId));
    }


    /**
     * 获取当前用户的所有问题的提交记录，用于个人中心进行展示
     * @param request
     * @param current
     * @param size
     * @return
     */
    @ApiOperation(value = "获取当前用户的所有提交记录",notes = "querySubmissionsList")
    @GetMapping("/submissions/list")
    public BaseResponse<PageInfo<QuestionSubmit>> querySubmissionsList(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer current,@RequestParam(defaultValue = "10") Integer size) {
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);

        User loginUser = (User) request.getSession().getAttribute(UserFeignClient.USER_LOGIN_STATE);
        Long userId = loginUser.getId();
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        PageHelper.startPage(current, size);
        List<QuestionSubmit> questionSubmitPage = questionSubmitMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(questionSubmitPage);
        return ResultUtils.success(pageInfo);
    }

}
