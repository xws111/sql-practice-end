package com.xws111.sqlpractice.judge.controller;

import com.xws111.sqlpractice.judge.service.JudgeService;
import com.xws111.sqlpractice.model.vo.JudgeInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Date 2024/5/3 14:58
 * @Version 1.0
 * @Author xg
 */
@RestController
public class JudgeController {
    @Resource
    JudgeService judgeService;

    @PostMapping("/judge")
    public JudgeInfo judge(String sql) {
        return judgeService.judge(sql);
    }
}
