package com.xws111.sqlpractice.question.controller.inner;

import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BussinessException;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.vo.JudgeInfo;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import com.xws111.sqlpractice.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/get")
    public QuestionSubmit getQuestionSubmitById(Long id) {
        if (id < 0) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        return questionSubmitService.getById(id);
    }
    @GetMapping("/answer")
    public String getAnswerById(Long id) {
        if (id < 0) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
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
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setResult(judgeInfo.getResult());
        questionSubmit.setStatus(2);
        questionSubmit.setId(judgeInfo.getId());
        questionSubmitService.updateById(questionSubmit);
    }


}
