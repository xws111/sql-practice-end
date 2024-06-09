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

    // region 增删改查

    /**
     * 创建
     *
     * @param questionAddRequest
     * @param request
     * @return
     */
    @ApiOperation("管理员新增问题接口")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<String> tags = questionAddRequest.getTags();
        User loginUser = userFeignClient.getLoginUser(request);
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionId = question.getId();
        QuestionTag questionTag = new QuestionTag();
        // 添加标签
        if (tags != null) {
            // todo 添加关系表
        }
        return ResultUtils.success(newQuestionId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @ApiOperation("管理员删除问题接口")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userFeignClient.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!userFeignClient.isAdmin(user)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = questionService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param questionUpdateRequest
     * @return
     */
    @ApiOperation("管理员更新问题接口")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        List<String> tags = questionUpdateRequest.getTags();

        List<JudgeCase> judgeCase = questionUpdateRequest.getJudgeCase();
        long id = questionUpdateRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @ApiOperation("管理员获取指定 id 问题接口")
    public BaseResponse<Question> getQuestionById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        User loginUser = userFeignClient.getLoginUser(request);
        // 不是本人或管理员，不能直接获取所有信息
        if (!userFeignClient.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return ResultUtils.success(question);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @ApiOperation("获取指定 id 问题接口")
    @GetMapping("/{id}")
    public BaseResponse<QuestionVO> getQuestionVOById(@PathVariable Long id, HttpServletRequest request) {
        if (id==null||id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(questionService.getQuestionVOById(id, request));
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param questionListRequest
     * @param request
     * @return
     */
    @ApiOperation("获取问题列表接口")
    public BaseResponse<Page<QuestionListVO>> listQuestionVOByPage(@RequestBody QuestionListRequest questionListRequest,
                                                                   HttpServletRequest request) {
        long current = questionListRequest.getCurrent();
        long size = questionListRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionListRequest));
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));

    }


    /**
     * 分页获取题目列表
     *
     * @param questionListRequest
     * @return
     */
    @ApiOperation("分页获取题目列表接口")
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionListVO>> listQuestionByPage(@RequestBody QuestionListRequest
                                                                             questionListRequest) {
        long current = questionListRequest.getCurrent();
        long size = questionListRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionListVO> page = questionService.getQuestionList(current, size);
        return ResultUtils.success(page);
    }


    // endregion

    /**
     * 编辑（用户）
     *
     * @param questionEditRequest
     * @param request
     * @return
     */
    @ApiOperation("管理员编辑问题接口")
    public BaseResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest questionEditRequest,
                                              HttpServletRequest request) {
        if (questionEditRequest == null || questionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionEditRequest, question);
        List<String> tags = questionEditRequest.getTags();
//        if (tags != null) {
//            question.setTags(GSON.toJson(tags));
//        }
        List<JudgeCase> judgeCase = questionEditRequest.getJudgeCase();
        // 参数校验
        questionService.validQuestion(question, false);
        User loginUser = userFeignClient.getLoginUser(request);
        long id = questionEditRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!userFeignClient.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    @ApiOperation("用户获取题目列表接口")
    public BaseResponse<Page<QuestionListVO>> getQuestionList(Long current, Long size) {
        Page<QuestionListVO> page = questionService.getQuestionList(current, size);
        return ResultUtils.success(page);
    }

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return 提交记录的 id
     */
    @ApiOperation("用户提交代码接口")
    public BaseResponse<Long> submitQuestion(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                             HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(questionSubmitAddRequest.getCode())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不能提交空语句！");
        }
        // 登录才提交
        final User loginUser = userFeignClient.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }

    /**
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @ApiOperation("分页获取题目提交列表（除了管理员外，普通用户只能看到非答案、提交代码等公开信息）")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userFeignClient.getLoginUser(request);
        // 返回脱敏信息
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));

    }

    /**
     * 获取判题结果
     */
    @ApiOperation("根据提交 id 获取判题结果接口")
    public QuestionSubmit getSubmitResult(long id) {
        return questionSubmitService.getById(id);
    }

}
