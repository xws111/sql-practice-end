package com.xws111.sqlpractice.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xws111.sqlpractice.mapper.QuestionTagMapper;
import com.xws111.sqlpractice.question.service.QuestionTagService;
import com.xws111.sqlpractice.model.entity.QuestionTag;
import org.springframework.stereotype.Service;

/**
* @author xg
* @description 针对表【question_tag(问题-标签关系表)】的数据库操作Service实现
* @createDate 2024-05-03 22:04:11
*/
@Service
public class QuestionTagServiceImpl extends ServiceImpl<QuestionTagMapper, QuestionTag>
    implements QuestionTagService {

}




