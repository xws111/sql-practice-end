package com.xws111.sqlpractice.judge.service;

import com.xws111.sqlpractice.model.vo.JudgeInfo;

/**
 * @Description:
 * @Date 2024/5/3 15:13
 * @Version 1.0
 * @Author xg
 */
public interface JudgeService {
    /**
     * 判题
     */
    JudgeInfo judge(Long id);
}
