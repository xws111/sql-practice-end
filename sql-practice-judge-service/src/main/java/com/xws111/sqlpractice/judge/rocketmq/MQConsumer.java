package com.xws111.sqlpractice.judge.rocketmq;

import com.xws111.sqlpractice.judge.service.JudgeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@RocketMQMessageListener(topic = "QUESTION_TOPIC", selectorExpression = "submitId", consumerGroup = "judge")
@Component
public class MQConsumer implements RocketMQListener<Long> {
    @Resource
    JudgeService judgeService;
    @Override
    public void onMessage(Long id) {
        log.info("judging...");
        judgeService.judge(id);
    }
}
