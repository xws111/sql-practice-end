package com.xws111.sqlpractice.user.service.impl;  
  
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;  
import com.xws111.sqlpractice.common.BaseResponse;  
import com.xws111.sqlpractice.common.ErrorCode;  
import com.xws111.sqlpractice.common.PageResponse;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.mapper.UserMapper;  
import com.xws111.sqlpractice.model.dto.user.*;  
import com.xws111.sqlpractice.model.entity.User;  
import com.xws111.sqlpractice.model.vo.UserVO;  
import com.xws111.sqlpractice.user.service.AdminUserService;  
import lombok.RequiredArgsConstructor;  
import org.apache.commons.lang3.StringUtils;  
import org.springframework.stereotype.Service;  
  
import java.util.Objects;  
  
  
/**  
 * @author Fancier  
 * @version 1.0  
 * @description: 后台管理端业务层  
 * @date 2024/6/9 15:31  
 */@Service  
@RequiredArgsConstructor  
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {  
  
    private final UserMapper userMapper;  
  
    /**  
     * 根据 id 删除用户  
     * @param deleteRequest  
     * @return  
     */  
    @Override  
    public BaseResponse deleteUser(UserDeleteRequest deleteRequest) {  
        removeById(deleteRequest.getId());  
        return BaseResponse.success();  
    }  
  
    /**  
     * 用户模糊查询 分页  
     * @return  
     */  
    @Override  
    public PageResponse fuzzyPageQuery(UserQueryRequest userQueryRequest) {  
        Page<User> page =  userMapper.fuzzyPageQuery(userQueryRequest);  
        return PageResponse.success(page, UserVO.class);  
    }  
  
    /**  
     * 根据id查询用户  
     * @param searchRequest  
     * @return  
     */  
    @Override  
    public BaseResponse searchUser(UserSearchRequest searchRequest) {  
        return BaseResponse.success(getById(searchRequest.getId()));  
    }  
  
    /**  
     * 后台管理端新增用户  
     * @param addRequest  
     * @return  
     */  
    @Override  
    public BaseResponse addUser(UserAddRequest addRequest) {
        String account = addRequest.getAccount();
        String userName = addRequest.getUsername();
        // 参数校验  
        if (StringUtils.isBlank(account) || StringUtils.isAnyBlank(account)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不合规");
        }  
        if (StringUtils.isBlank(userName) || StringUtils.isAnyBlank(userName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名不合规");
        }

        User pre = userMapper.selectByAccount(account);

        if (Objects.nonNull(pre)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        User user = new User();
        user.setPassword("123456");
        BeanUtil.copyProperties(addRequest, user);
        save(user);
        return BaseResponse.success();  
    }  
  
    /**  
     * 后台管理端 更新用户信息  
     * @param userUpdateRequest  
     * @return  
     */  
    @Override  
    public BaseResponse updateUser(UserUpdateRequest userUpdateRequest) {  
        // 判断账号是否存在  
        User pre = getById(userUpdateRequest.getId());  
        if (Objects.isNull(pre)) {  
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }  
        // 执行更新操作  
        User user = new User();  
        BeanUtil.copyProperties(userUpdateRequest, user);  
        updateById(user);  
        return BaseResponse.success();  
    }  
}