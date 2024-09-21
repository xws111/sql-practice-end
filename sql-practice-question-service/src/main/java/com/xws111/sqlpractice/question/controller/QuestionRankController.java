package com.xws111.sqlpractice.question.controller;

import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.model.vo.RankListVO;
import com.xws111.sqlpractice.model.vo.RankVO;
import com.xws111.sqlpractice.question.service.QuestionSubmitService;
import com.xws111.sqlpractice.security.annotation.LoginCheck;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 题目排行类
 */

@RestController
@RequestMapping("/rank")
@Api(tags = "题目排行榜接口")
@Slf4j
public class QuestionRankController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/overall")
    public BaseResponse<List<RankListVO>> getOverallRankList(HttpServletRequest request) {
        return ResultUtils.success(questionSubmitService.getOverallRankList(request));
    }

    @GetMapping("/question")
    public BaseResponse<List<RankListVO>> getQuestionRankList(HttpServletRequest request, @RequestParam Integer id) {
        return ResultUtils.success(questionSubmitService.getQuestionRankList(request,id));
    }

    /**
     * 获取自己的排名
     * @param request
     * @return RankVO
     */
    @LoginCheck
    @GetMapping("/my/overall")
    public BaseResponse<RankVO> getMyRankList(HttpServletRequest request) {
        return ResultUtils.success(questionSubmitService.getQuestionRankByCurrentId(request));
    }
}
