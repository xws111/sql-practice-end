package com.xws111.sqlpractice.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.mapper.QuestionSubmitMapper;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionSubmitResultVO;
import com.xws111.sqlpractice.model.vo.QuestionSubmitVO;
import com.xws111.sqlpractice.question.rocketmq.MQProducer;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import com.xws111.sqlpractice.service.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
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
        questionSubmit.setStatus(1);
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
    public QuestionSubmitResultVO getSubmitQuestion(HttpServletRequest request, Long questionSubmitId) {
        //获取当前用户
        User loginUser = userFeignClient.getLoginUser(request);
        //查询
        return questionSubmitMapper.getQuestionSubmitVOById(questionSubmitId);
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        return null;
    }

    @Override
    public QueryWrapper<QuestionSubmit> getUserQueryWrapper(HttpServletRequest request) {
        //获取当前用户id
        User loginUser = userFeignClient.getLoginUser(request);
        Long userId = loginUser.getId();
        if(null == userId){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR,"请登录");
        }
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.isNotNull("result");
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit) {
        if(questionSubmit == null){
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        return questionSubmitVO;
    }

    @Override
    public List<QuestionSubmitVO> getQuestionSubmitVOPage(List<QuestionSubmit> questionSubmitPage) {
//        if (CollectionUtils.isEmpty(userList)) {
//            return new ArrayList<>();
//        }
//        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(questionSubmitPage)){
            return new ArrayList<>();
        }
        return questionSubmitPage.stream().map(this::getQuestionSubmitVO).collect(Collectors.toList());
    }
}




