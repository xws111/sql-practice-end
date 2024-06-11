package com.xws111.sqlpractice.question.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.DeleteRequest;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.exception.ThrowUtils;
import com.xws111.sqlpractice.model.dto.question.QuestionAddRequest;
import com.xws111.sqlpractice.model.dto.question.QuestionListRequest;
import com.xws111.sqlpractice.model.dto.question.QuestionUpdateRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionAllVO;
import com.xws111.sqlpractice.model.vo.QuestionListAllVO;
import com.xws111.sqlpractice.question.service.*;
import com.xws111.sqlpractice.service.UserFeignClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/question")
@Slf4j
public class AdminQuestionController {

    @Resource
    private AdminQuestionService adminQuestionService;

    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private TagService tagService;
    @Resource
    private QuestionTagService questionTagService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    private final static Gson GSON = new Gson();


    @ApiOperation("管理员新增问题接口")
    @PostMapping("/add")
    @Transactional
    //    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 需要管理员权限
        User loginUser = userFeignClient.getLoginUser(request);
        if (loginUser.getRole() == 0) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "您无权操作");
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<String> tags = questionAddRequest.getTags();
        boolean resultInsertQuestion = adminQuestionService.save(question);
        Long afterInsertQuestionId = question.getId();
        ThrowUtils.throwIf(!resultInsertQuestion, ErrorCode.OPERATION_ERROR);
        long newQuestionId = question.getId();
        // 添加标签与问题的对应关系
        if (tags != null) {
           // 添加进关系表
           adminQuestionService.addQuestionTag(tags, afterInsertQuestionId);
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
    @PostMapping("/delete")
    @Transactional
    //    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 需要管理员权限
        User loginUser = userFeignClient.getLoginUser(request);
        if (loginUser.getRole() == 0) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "您无权操作");
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = adminQuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = adminQuestionService.removeById(id);
        //删除关联表数据
        adminQuestionService.removeQuestionTag(id);
        return ResultUtils.success(result);
    }

    /**
     * 更新（仅管理员）
     *
     * @param questionUpdateRequest
     * @return
     */
    @ApiOperation("管理员更新问题接口")
    @PostMapping("/update")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @Transactional
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest, HttpServletRequest request) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 需要管理员权限
        User loginUser = userFeignClient.getLoginUser(request);
        if (loginUser.getRole() == 0) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "您无权操作");
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        List<String> tags = questionUpdateRequest.getTags();

        long id = questionUpdateRequest.getId();
        // 判断是否存在
        Question oldQuestion = adminQuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = adminQuestionService.updateById(question);
        //更新->先删除再插入
        adminQuestionService.removeQuestionTag(id);
        adminQuestionService.addQuestionTag(tags,id);
        return ResultUtils.success(result);
    }


    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @ApiOperation("管理员获取指定 id 问题接口")
    @GetMapping("/search")
    //    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<QuestionAllVO> getQuestionById(@RequestParam long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = adminQuestionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 需要管理员权限
        User loginUser = userFeignClient.getLoginUser(request);
        if (loginUser.getRole() == 0) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "您无权操作");
        }
        QuestionAllVO questionAllVO = adminQuestionService.selectQuestionTag(question);

        return ResultUtils.success(questionAllVO);
    }


    /**
     * 分页获取列表（封装类）
     *
     * @param questionListRequest
     * @param request
     * @return
     */
    @ApiOperation("获取问题列表接口")
    @GetMapping("/list/page")
    //    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PageInfo<QuestionAllVO>> listQuestionVOByPage(@ModelAttribute QuestionListRequest questionListRequest, HttpServletRequest request) {
        // 需要管理员权限
        User loginUser = userFeignClient.getLoginUser(request);
        if (loginUser.getRole() == 0) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "您无权操作");
        }
        return ResultUtils.success(adminQuestionService.getQuestionVOPage(questionListRequest, request));

    }
}
