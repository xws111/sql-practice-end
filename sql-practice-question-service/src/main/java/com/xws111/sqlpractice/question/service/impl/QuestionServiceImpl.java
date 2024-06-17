package com.xws111.sqlpractice.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.constant.CommonConstant;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.exception.ThrowUtils;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.mapper.TagMapper;
import com.xws111.sqlpractice.model.dto.question.QuestionListRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.model.vo.QuestionVO;
import com.xws111.sqlpractice.question.service.QuestionService;
import com.xws111.sqlpractice.service.UserFeignClient;
import com.xws111.sqlpractice.utils.SqlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xg
 * @description 针对表【question(题目表)】的数据库操作Service实现
 * @createDate 2024-05-03 22:03:56
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private QuestionMapper questionMapper;


    /**
     * 校验题目是否合法
     *
     * @param question
     * @param add
     */
    @Override
    public void validQuestion(Question question, boolean add) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        String description = question.getContent();
        String answer = question.getAnswer();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, description, answer), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "答案过长");
        }
    }

    /**
     * 获取查询包装类（用户根据哪些字段查询，根据前端传来的请求对象，得到 mybatis 框架支持的查询 QueryWrapper 类）
     *
     * @param questionListRequest
     * @return
     */
    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionListRequest questionListRequest) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if (questionListRequest == null) {
            return queryWrapper;
        }
        Long id = questionListRequest.getId();
        String title = questionListRequest.getTitle();
        String sortField = questionListRequest.getSortField();
        String sortOrder = questionListRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.eq("deleted", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionVO getQuestionVOById(Long id, HttpServletRequest request) {
        // 登录后才能获取题目详细信息
        User user = (User) request.getSession().getAttribute(UserFeignClient.USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(this.getById(id), questionVO);
        List<String> tags = tagMapper.getTagNamesByQuestionId(id);
        questionVO.setTags(tags);
        return questionVO;
    }

    @Override
    public Page<QuestionListVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request) {
        List<Question> questionList = questionPage.getRecords();
        return null;
    }

    @Override
    public PageInfo<QuestionListVO> getQuestionListPage(QuestionListRequest pageRequest) {
        // 分页插件
        PageHelper.startPage(pageRequest.getCurrent(), pageRequest.getPageSize());
        Long id = pageRequest.getId();
        String keyword = pageRequest.getTitle();
        // todo 排序
        String sortField = pageRequest.getSortField();
        String sortOrder = pageRequest.getSortOrder();
        // 根据 id、标题分页获取题目信息
        List<QuestionListVO> questionListVO = questionMapper.getQuestionsVOList(id, keyword);
        PageInfo<QuestionListVO> pageInfo = new PageInfo<>(questionListVO);
        return pageInfo;
    }

}




