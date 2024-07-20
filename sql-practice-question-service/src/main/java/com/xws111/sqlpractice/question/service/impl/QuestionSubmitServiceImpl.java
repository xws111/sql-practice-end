package com.xws111.sqlpractice.question.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.constant.CommonConstant;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.mapper.QuestionSubmitMapper;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.enums.QuestionSubmitStatusEnum;
import com.xws111.sqlpractice.model.vo.QuestionSubmitVO;
import com.xws111.sqlpractice.question.rocketmq.MQProducer;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import com.xws111.sqlpractice.service.QuestionFeignClient;
import com.xws111.sqlpractice.service.UserFeignClient;
import com.xws111.sqlpractice.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author xg
* @description 针对表【question_submit(提交记录表)】的数据库操作Service实现
* @createDate 2024-05-03 22:04:06
*/
@Slf4j
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {

    @Resource
    MQProducer mqProducer;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    UserFeignClient userFeignClient;

    @Resource
    private QuestionSubmitMapper questionSubmitMapper;
    @Qualifier("com.xws111.sqlpractice.service.QuestionFeignClient")
    @Autowired
    private QuestionFeignClient questionFeignClient;

    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(questionSubmitAddRequest.getCode())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不能提交空语句！");
        }
        long questionId = questionSubmitAddRequest.getQuestionId();
        String code = questionSubmitAddRequest.getCode();
        String language = questionSubmitAddRequest.getLanguage();
        Long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(code);
        questionSubmit.setLanguage(language);
        questionSubmit.setUserId(userId);
        //插入数据库
        this.save(questionSubmit);
        long id = questionSubmit.getId();
        //更改状态为判题中
        mqProducer.send(id);
        log.info("提交题目：" + id + "提交数加 1 ");

        //更新提交数 + 1
        questionMapper.incrementSubmitCount(questionId);
        return id;
    }

    @Override
    public List<QuestionSubmit> getQuestionSubmissionByQuestionId(HttpServletRequest request, Long questionId) {
        // 判断登录
        User loginUser = (User) request.getSession().getAttribute(UserFeignClient.USER_LOGIN_STATE);
        if (loginUser.getId() <= 0) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", loginUser.getId());
        queryWrapper.eq("question_id", questionId);
        return questionSubmitMapper.selectList(queryWrapper);
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        queryWrapper.like(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "question_id", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "user_id", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    @Override
    public List<QuestionSubmitVO> getQuestionVOPage(List<QuestionSubmit> questionSubmitList, HttpServletRequest request) {

        if (CollUtil.isEmpty(questionSubmitList)) {
            return new ArrayList<>();
        }
        // 为了找到这个题目是谁的写的
        Set<Long> userIdSet = questionSubmitList.stream().map(QuestionSubmit::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdListMap = userFeignClient.listByIds(userIdSet).stream().collect(Collectors.groupingBy(User::getId));

        // 为了找到找个题目的标题是什么
        Set<Long> quesitonIdSet = questionSubmitList.stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toSet());
        Map<Long, List<Question>> questionIdMap = questionFeignClient.listByIds(quesitonIdSet).stream().collect(Collectors.groupingBy(Question::getId));


        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            QuestionSubmitVO questionSubmitVo = new QuestionSubmitVO();
            BeanUtils.copyProperties(questionSubmit, questionSubmitVo);
            Long userId = questionSubmit.getUserId();
            if (ObjectUtils.isNotEmpty(userId)) {
                questionSubmitVo.setUserName(userIdListMap.get(userId).get(0).getUsername());
            }
            if (ObjectUtils.isNotEmpty(questionSubmit.getQuestionId())) {
                questionSubmitVo.setQuestionTitle(questionIdMap.get(questionSubmit.getQuestionId()).get(0).getTitle());
            }
            questionSubmitVo.setStatusStr(Objects.requireNonNull(QuestionSubmitStatusEnum.getEnumByValue(questionSubmit.getStatus())).getText());
            questionSubmitVo.setSubmitTime(questionSubmit.getCreateTime());
            return questionSubmitVo;
        }).collect(Collectors.toList());
        return questionSubmitVOList;
    }

}




