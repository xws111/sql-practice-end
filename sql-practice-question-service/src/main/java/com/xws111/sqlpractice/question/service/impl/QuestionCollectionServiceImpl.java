package com.xws111.sqlpractice.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.mapper.QuestionCollectionMapper;
import com.xws111.sqlpractice.model.entity.QuestionCollection;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.question.service.QuestionCollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wind
 * @description 针对表【question_collection(题目收藏表)】的数据库操作Service实现
 * @createDate 2024-07-21 14:04:06
 */
@Service
public class QuestionCollectionServiceImpl extends ServiceImpl<QuestionCollectionMapper, QuestionCollection>
    implements QuestionCollectionService{

    @Resource
    private QuestionCollectionMapper questionCollectionMapper;

    /**
     * 收藏题目
     * @param questionId
     * @param loginUser
     * @return
     */
    @Override
    public boolean doQuestionCollection(Long questionId, User loginUser) {
        if(questionId == null || questionId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long userId = loginUser.getId();
        QuestionCollection questionCollection = new QuestionCollection();
        questionCollection.setQuestionId(questionId);
        questionCollection.setUserId(userId);
        return this.save(questionCollection);
    }

    /**
     * 取消收藏
     * @param questionId
     * @param loginUser
     * @return
     */
    @Override
    public boolean cancelQuestionCollection(Long questionId, User loginUser) {
        if(questionId == null || questionId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long userId = loginUser.getId();
        return this.remove(new QueryWrapper<QuestionCollection>().eq("question_id", questionId).eq("user_id", userId));
    }

    /**
     * 获取用户收藏的题目列表
     * @param loginUser
     * @return
     */
    @Override
    public List<QuestionListVO> getQuestionCollectionList(User loginUser) {
        Long userId = loginUser.getId();
        List<QuestionListVO> questionCollectionList = questionCollectionMapper.getQuestionCollectionList(userId);
        return questionCollectionList;
    }
}




