package com.xws111.sqlpractice.question.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.mapper.TagMapper;
import com.xws111.sqlpractice.model.dto.question.QuestionListRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.model.vo.QuestionVO;
import com.xws111.sqlpractice.question.service.QuestionService;
import com.xws111.sqlpractice.service.UserFeignClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        List<QuestionListVO> questionListVO = questionMapper.getQuestionsVOList(id, keyword, pageRequest.getTags());
        PageInfo<QuestionListVO> pageInfo = new PageInfo<>(questionListVO);
        return pageInfo;
    }

    @Override
    public List<Integer> getQuestionACList(Long userId) {
        List<Integer> acList = questionMapper.getQuestionACList(userId);
        return acList;
    }

}




