package com.xws111.sqlpractice.question.controller.inner;

import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.vo.JudgeInfo;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import com.xws111.sqlpractice.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private QuestionMapper questionMapper;

    /**
     * 根据id获取提交记录
     * @param id
     * @return
     */
    @PostMapping("/get")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("id") long id) {
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return questionSubmitService.getById(id);
    }
    @PostMapping("/answer")
    public String getAnswerById(@RequestParam("id") Long id) {
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return questionMapper.getQuestionAnswerById(id);
    }


    /**
     * 更新状态和结果
     *
     * @param judgeInfo
     */
    @Override
    public void updateSubmitResult(JudgeInfo judgeInfo) {
        if (judgeInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setResult(judgeInfo.getResult());
        questionSubmit.setStatus(2);
        questionSubmit.setId(judgeInfo.getId());
        questionSubmitService.updateById(questionSubmit);
    }


}
