package com.xws111.sqlpractice.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xws111.sqlpractice.mapper.QuestionSubmitMapper;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionSubmitVO;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
* @author xg
* @description 针对表【question_submit(提交记录表)】的数据库操作Service实现
* @createDate 2024-05-03 22:04:06
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {

    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        return 0;
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




