package com.xws111.sqlpractice.question.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xws111.sqlpractice.common.BaseResponse;

import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.exception.ThrowUtils;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionSubmitResultVO;
import com.xws111.sqlpractice.model.vo.QuestionSubmitVO;
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


    /**
     * 用户提交代码接口
     * @param request
     * @param questionSubmitAddRequest
     * @return
     */
    @ApiOperation(value = "用户提交代码接口",notes = "questionSubmit")
    @PostMapping("/add")
    public BaseResponse<Long> questionSubmit(HttpServletRequest request,
                                             @RequestBody @Valid QuestionSubmitAddRequest questionSubmitAddRequest) {
            // 登录才提交
            final User loginUser = userFeignClient.getLoginUser(request);
            long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
            return ResultUtils.success(questionSubmitId);
    }

    /**
     * 用户查询提交结果的接口
     * @param request
     * @param questionSubmitId
     * @return
     */
    @ApiOperation(value = "用户查询提交结果的接口",notes = "questionSubmitResult")
    @GetMapping("/{questionSubmitId}/result")
    public BaseResponse<QuestionSubmitResultVO> questionSubmitResult(HttpServletRequest request, @PathVariable Long questionSubmitId){
        return ResultUtils.success(questionSubmitService.getSubmitQuestion(request, questionSubmitId));
    }

    /**
     * 用户查询结果接口
     * @param questionId
     * @return
     */
    //TODO 不查询未成功的
    @ApiOperation(value = "用户查询结果接口",notes = "querySubmissionsByQuestionId")
    @GetMapping("/{questionId}/submissions")
    public BaseResponse<QuestionSubmit> querySubmissionsByQuestionId (@PathVariable long questionId) {
        return ResultUtils.success(questionSubmitService.getById(questionId));
    }


    /**
     * 获取当前用户的所有提交记录
     * @param request
     * @param current
     * @param size
     * @return
     */
    @ApiOperation(value = "获取当前用户的所有提交记录",notes = "querySubmissionsList")
    @GetMapping("/submissions/list")
    public BaseResponse<Page<QuestionSubmitVO>> querySubmissionsList(HttpServletRequest request, long current, long size) {
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getUserQueryWrapper(request));
        Page<QuestionSubmitVO> questionSubmitByUserIdListResponsePage = new Page<>(current, size, questionSubmitPage.getTotal());
        log.info(questionSubmitByUserIdListResponsePage.toString());
//        questionSubmitService.get
//        questionSubmitByUserIdListResponsePage.setRecords()
        List<QuestionSubmitVO> questionSubmitVO = questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage.getRecords());
        questionSubmitByUserIdListResponsePage.setRecords(questionSubmitVO);
        return ResultUtils.success(questionSubmitByUserIdListResponsePage);
    }
}
