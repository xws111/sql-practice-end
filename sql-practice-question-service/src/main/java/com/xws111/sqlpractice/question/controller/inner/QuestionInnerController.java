package com.xws111.sqlpractice.question.controller.inner;

import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.vo.JudgeInfo;
import com.xws111.sqlpractice.question.service.QuestionService;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import com.xws111.sqlpractice.service.QuestionFeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionService questionService;

    /**
     * 根据id获取提交记录
     * @param id
     * @return
     */
    @GetMapping("/get")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("id") long id) {
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return questionSubmitService.getById(id);
    }
    @GetMapping("/answer")
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
    @Transactional
    public void updateSubmitResult(JudgeInfo judgeInfo) {
        if (judgeInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setResult(judgeInfo.getResult());
        questionSubmit.setStatus(judgeInfo.getStatus());
        questionSubmit.setId(judgeInfo.getId());
        questionSubmit.setOutput(judgeInfo.getQueryResult());
        questionSubmit.setDuration(judgeInfo.getTime());
        questionSubmitService.updateById(questionSubmit);
    }

    /**
     * 通过数加一
     * @param id
     */
    @Override
    public void incrementAccepted(Long id) {
        questionMapper.incrementAcceptedCount(id);
    }


    /**
     * 根据ids拿到对应的题目数据
     * @param idList
     * @return
     */
    @GetMapping("/get/ids")
    public List<Question> listByIds(@RequestParam("idList") Collection<Long> idList){
        return questionService.listByIds(idList);
    }

}
