package com.xws111.sqlpractice.judge.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BussinessException;
import com.xws111.sqlpractice.judge.service.JudgeService;
import com.xws111.sqlpractice.model.dto.docker.ExecuteResult;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.vo.JudgeInfo;
import com.xws111.sqlpractice.service.QuestionFeignClient;
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
@Service
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Override
    public JudgeInfo judge(Long id) {
        // 1. 通过题目 id 拿到提交信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(id);
        // 2. 将题目代码放入远程沙箱跑
        String code = questionSubmit.getCode();
        ExecuteResult executeResult = postToRemoteApi(code);
        // 3. 将返回的结果进行判断是否正确
        String jsonResult = executeResult.getJsonResult();
        // 4. 判题
        String answer = questionFeignClient.getAnswerById(id);
        boolean result = compareJsonStrings(jsonResult, answer);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(executeResult.getTime());
        judgeInfo.setQueryResult(executeResult.getMessage());
        judgeInfo.setId(id);
        if (result) {
            judgeInfo.setResult("完全正确恭喜你又进一步！");
        } else {
            judgeInfo.setResult("错误！动动🧠！");
        }
        // 5. 更新数据库结果
        questionFeignClient.updateSubmitResult(judgeInfo);
        return judgeInfo;
    }

    private ExecuteResult postToRemoteApi(String sql) {
        // 设置请求 URL 和请求体
        String url = "http://remote-server-url/api/endpoint";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("sql", "value1");
        // 发起 POST 请求，接收响应
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);
        // 检查请求是否成功
        if (response.getStatusCode().is2xxSuccessful()) {
            // 使用 Gson 将 JSON 字符串转换为自定义对象
            Gson gson = new Gson();
            return gson.fromJson(response.getBody(), ExecuteResult.class);
        } else {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "运行出错，请检查代码");
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
