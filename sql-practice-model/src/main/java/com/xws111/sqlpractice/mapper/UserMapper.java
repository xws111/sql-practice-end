package com.xws111.sqlpractice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xws111.sqlpractice.model.dto.user.UserQueryRequest;
import com.xws111.sqlpractice.model.entity.User;
import org.apache.commons.lang3.StringUtils;

/**
* @author xg
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-05-06 18:28:36
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
    default Page<User> fuzzyPageQuery(UserQueryRequest userQueryRequest) {
        Page<User> page = userQueryRequest.<User>toMpPage();
        return selectPage(page, Wrappers.<User>lambdaQuery()
                .like(StringUtils.isNotBlank(userQueryRequest.getUserName()), User::getUsername, userQueryRequest.getUserName())
                .like(StringUtils.isNotBlank(userQueryRequest.getAccount()), User::getAccount, userQueryRequest.getAccount())
        );
    }

    default User selectByAccount(String account) {
        return selectOne(Wrappers.<User>lambdaQuery()
               .eq(StringUtils.isNotBlank(account), User::getAccount, account)
        );
    }
}




