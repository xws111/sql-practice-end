package com.xws111.sqlpractice.judge.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.judge.json.JsonUtils;
import com.xws111.sqlpractice.judge.service.JudgeService;
import com.xws111.sqlpractice.mapper.QuestionMapper;
import com.xws111.sqlpractice.mapper.QuestionSubmitMapper;
import com.xws111.sqlpractice.model.dto.docker.ExecuteResult;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.vo.JudgeInfo;
import com.xws111.sqlpractice.service.QuestionFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Date 2024/5/3 15:15
 * @Version 1.0
 * @Author xg
 */
@Slf4j
@Service
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Override
    public JudgeInfo judge(Long id) {
        JudgeInfo judgeInfo = new JudgeInfo();
        // 1. 通过题目 id 拿到提交信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(id);
        // 2. 将题目代码放入远程沙箱跑
        String code = questionSubmit.getCode();
        ExecuteResult executeResult = postToRemoteApi(code);
        if (executeResult.getJsonResult() == null) {
            judgeInfo.setResult("sql 语法错误！");
            judgeInfo.setId(id);
            log.info("已更新数据库中的提交记录。");
            questionFeignClient.updateSubmitResult(judgeInfo);
            return judgeInfo;
        }
        // 3. 将返回的结果进行判断是否正确
        String jsonResult = executeResult.getJsonResult();
        // 4. 判题
        String answer = questionFeignClient.getAnswerById(questionSubmit.getQuestionId());
        jsonResult = jsonResult.replaceAll("=([^{},]+)", "=\"$1\"");
        answer = answer.replaceAll("=([^{},]+)", "=\"$1\"");
        boolean result = compareJsonStrings(jsonResult, answer);

        judgeInfo.setTime(executeResult.getTime());
        judgeInfo.setQueryResult(executeResult.getMessage());
        judgeInfo.setId(id);
        if (result) {
            log.info("答案正确！");
            judgeInfo.setResult("完全正确, 恭喜你又进一步！");
        } else {
            log.info("答案错误！");
            judgeInfo.setResult("错误！你再好好想想！");
        }
        // 5. 更新数据库结果
        log.info("已更新数据库中的提交记录。");
        questionFeignClient.updateSubmitResult(judgeInfo);
        return judgeInfo;
    }

    private ExecuteResult postToRemoteApi(String sql) {
        // 设置请求 URL 和请求体
        String url = "http://118.89.133.136:8080/execute";
//        String url = "http://192.168.17.128:8080/execute";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("inputSQL", sql);
        // 发起 POST 请求，接收响应
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);
        // 检查请求是否成功
        if (response.getStatusCode().is2xxSuccessful()) {
            ExecuteResult executeResult = new ExecuteResult();
            JsonUtils.responseToExecuteResult(response.getBody(), executeResult);
            return executeResult;
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "运行出错，请检查代码");
        }
    }
    public static boolean compareJsonStrings(String json1, String json2) {
        // 使用 Gson 的 JsonParser 将字符串解析为 JsonElement
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        JsonElement element1 = parser.parse(json1);
        JsonElement element2 = parser.parse(json2);

        // 使用 JsonElement 的 equals 方法比较两个 JSON 对象
        return element1.equals(element2);
    }
}
