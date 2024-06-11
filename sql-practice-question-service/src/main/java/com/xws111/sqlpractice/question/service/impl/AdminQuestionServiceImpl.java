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
import com.xws111.sqlpractice.mapper.QuestionTagMapper;
import com.xws111.sqlpractice.mapper.TagMapper;
import com.xws111.sqlpractice.model.dto.question.QuestionListRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.QuestionTag;
import com.xws111.sqlpractice.model.entity.Tag;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.*;
import com.xws111.sqlpractice.question.service.AdminQuestionService;
import com.xws111.sqlpractice.question.service.QuestionTagService;
import com.xws111.sqlpractice.question.service.TagService;
import com.xws111.sqlpractice.service.UserFeignClient;
import com.xws111.sqlpractice.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author xg
 * @description 针对表【question(题目表)】的数据库操作Service实现
 * @createDate 2024-05-03 22:03:56
 */
@Service
public class AdminQuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements AdminQuestionService {

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private TagService tagService;
    @Resource
    private QuestionTagService questionTagService;

    @Resource
    private QuestionTagMapper questionTagMapper;

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
        String content = questionListRequest.getContent();
        List<String> tags = questionListRequest.getTags();
        String sortField = questionListRequest.getSortField();
        String sortOrder = questionListRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.eq(id > 0, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.eq("is_deleted", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionVO getQuestionVOById(Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserFeignClient.USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        QuestionVO questionVO = questionMapper.getQuestionContent(id);
        List<String> tags = tagMapper.getTagNamesByQuestionId(id);
        questionVO.setTags(tags);
        return questionVO;
    }

    @Override
    public PageInfo<QuestionAllVO> getQuestionVOPage(QuestionListRequest questionListRequest, HttpServletRequest request) {
        String title = questionListRequest.getTitle();
        String content = questionListRequest.getContent();
        if (StringUtils.isNotBlank(title)) {
            questionListRequest.setTitle("%" + title + "%");
        }
        if (StringUtils.isNotBlank(content)) {
            questionListRequest.setTitle("%" + content + "%");
        }
        PageHelper.startPage(questionListRequest.getCurrent(), questionListRequest.getPageSize());
        List<QuestionAllVO> questionAllVOByRequest = questionMapper.getQuestionAllVOByRequest(questionListRequest);
        return new PageInfo<>(questionAllVOByRequest);

    }

    @Override
    public Page<QuestionListVO> getQuestionList(long current, long size) {
        Page<QuestionListVO> page = new Page<>(current, size);

        List<QuestionListVO> questionListVO = questionMapper.getAllQuestions();
        List<QuestionTagVO> tags = questionMapper.getAllQuestionTags();
        // 建立问题 ID 到标签列表的映射
        Map<Long, List<String>> questionToTags = new HashMap<>();
        for (QuestionTagVO questionTag : tags) {
            questionToTags
                    .computeIfAbsent(questionTag.getQuestionId(), k -> new ArrayList<>())
                    .add(questionTag.getTagName());
        }
        // 将标签列表合并到问题对象中
        for (QuestionListVO question : questionListVO) {
            question.setTags(questionToTags.getOrDefault(question.getId(), Collections.emptyList()));
        }
        page.setRecords(questionListVO);
        return page;
    }

    @Override
    // todo 有待优化：批量保存、动态 sql
    // 可以实现：SELECT id FROM tags WHERE name IN ('tag1', 'tag2', 'tag3');
    // 使数据库只有一次查询
    public void addQuestionTag(List<String> tags, Long afterInsertQuestionId) {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestionId(afterInsertQuestionId);
        // todo 添加进关系表
        for (String tagName : tags) {
            //根据标签名查出tag表对应标签的id
            QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", tagName);
            Tag tag = tagService.getOne(queryWrapper);
            Integer tagId = tag.getId();
            //插入关联表
            questionTag.setTagId(tagId);
            boolean resultInsertQuestionTag = questionTagService.save(questionTag);
            ThrowUtils.throwIf(!resultInsertQuestionTag, ErrorCode.OPERATION_ERROR);
        }
    }

    @Override
    public void removeQuestionTag(Long id) {
        QueryWrapper<QuestionTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", id);
        List<QuestionTag> questionTagList = questionTagService.list(queryWrapper);

        Long questionTagId = questionTagList.get(0).getQuestionId();
        //删除关联表的数据
        boolean result = questionTagService.removeById(questionTagId);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

    }

    @Override
    public QuestionAllVO selectQuestionTag(Question question) {
        Long id = question.getId();
        QueryWrapper<QuestionTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", id);
        List<QuestionTag> questionTagList = questionTagService.list(queryWrapper);
        QuestionAllVO questionAllVO = new QuestionAllVO();
        List<String> tagList = new ArrayList<>();
        for (QuestionTag questionTag : questionTagList) {
            Tag tag = tagService.getById(questionTag.getTagId());
            tagList.add(tag.getName());
        }
        questionAllVO.setTags(tagList);
        BeanUtils.copyProperties(question, questionAllVO);
        return questionAllVO;
    }


}




