package com.xws111.sqlpractice.judge.json;

import com.google.gson.Gson;
import com.xws111.sqlpractice.model.dto.docker.ApiResponse;
import com.xws111.sqlpractice.model.dto.docker.ExecuteResult;

public class JsonUtils {
    public static void responseToExecuteResult(String json, ExecuteResult executeResult) {
        Gson gson = new Gson();
        ApiResponse response = gson.fromJson(json, ApiResponse.class);
        executeResult.setMessage(response.getJudgeInfo().getMessage());
        executeResult.setTime(response.getJudgeInfo().getTime());
        executeResult.setJsonResult(response.getJudgeInfo().getJsonResult());
    }
}
