package com.xws111.sqlpractice.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.mapper.QuestionSubmitMapper;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionSubmitVO;
import com.xws111.sqlpractice.question.rocketmq.MQProducer;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
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
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        return null;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        return null;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        return null;
    }
}




