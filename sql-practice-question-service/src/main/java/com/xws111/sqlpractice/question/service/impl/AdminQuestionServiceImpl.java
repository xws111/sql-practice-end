package com.xws111.sqlpractice.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.exception.ThrowUtils;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.mapper.TagMapper;
import com.xws111.sqlpractice.model.dto.question.AdminQuestionRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.QuestionTag;
import com.xws111.sqlpractice.model.entity.Tag;
import com.xws111.sqlpractice.question.service.AdminQuestionService;
import com.xws111.sqlpractice.question.service.QuestionTagService;
import com.xws111.sqlpractice.question.service.TagService;
import com.xws111.sqlpractice.service.UserFeignClient;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Override
    public PageInfo getQuestionList(AdminQuestionRequest questionListRequest, Integer current, Integer pageSize) {
        Long id = questionListRequest.getId();
        String title = questionListRequest.getTitle();
        List<String> tags = questionListRequest.getTags();
        Integer difficulty = questionListRequest.getDifficulty();
        PageHelper.startPage(current, pageSize);
        List<Question> questionList = questionMapper.getQuestionsList(id, title, tags, difficulty);
        return new PageInfo(questionList);
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



}




