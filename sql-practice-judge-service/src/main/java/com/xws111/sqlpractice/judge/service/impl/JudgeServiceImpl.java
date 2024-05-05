package com.xws111.sqlpractice.judge.service.impl;

import com.xws111.sqlpractice.judge.service.JudgeService;
import com.xws111.sqlpractice.model.vo.JudgeInfo;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Date 2024/5/3 15:15
 * @Version 1.0
 * @Author xg
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Override
    public JudgeInfo judge(String string) {
        JudgeInfo judgeInfo =  new JudgeInfo();
        judgeInfo.setMessage("test ok");
        return judgeInfo;
    }
}
