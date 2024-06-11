package com.xws111.sqlpractice.user.service.impl;  
  
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;
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
 */
@Service
@RequiredArgsConstructor  
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {  
  
    private final UserMapper userMapper;  
  
    /**  
     * 根据 id 删除用户  
     * @param deleteRequest  
     * @return  
     */  
    @Override  
    public BaseResponse<Boolean> deleteUser(UserDeleteRequest deleteRequest) {
        return ResultUtils.success(removeById(deleteRequest.getId()));
    }  
  
    /**
     * 用户模糊查询 分页
     *
     * @return
     */  
    @Override
    public BaseResponse<Page<UserVO>> fuzzyPageQuery(UserQueryRequest userQueryRequest) {
        Page<User> page = userMapper.fuzzyPageQuery(userQueryRequest);
        Page<UserVO> data = (Page<UserVO>) page.convert(user -> BeanUtil.copyProperties(user, UserVO.class));
        return ResultUtils.success(data);
    }  
  
    /**  
     * 根据id查询用户  
     * @param searchRequest  
     * @return  
     */  
    @Override  
    public BaseResponse<UserVO> searchUser(UserSearchRequest searchRequest) {
        User user = getById(searchRequest.getId());
        if (Objects.isNull(user)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        // DO 转 VO 后返回
        return ResultUtils.success(BeanUtil.copyProperties(user, UserVO.class));
    }  
  
    /**  
     * 后台管理端新增用户  
     * @param addRequest  
     * @return  
     */  
    @Override  
    public BaseResponse<Boolean> addUser(UserAddRequest addRequest) {
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
        // 设置默认密码 TODO 后续可能修改
        user.setPassword("123456");
        BeanUtil.copyProperties(addRequest, user);
        return ResultUtils.success(save(user));
    }  
  
    /**  
     * 后台管理端 更新用户信息  
     * @param userUpdateRequest  
     * @return  
     */  
    @Override  
    public BaseResponse<Boolean> updateUser(UserUpdateRequest userUpdateRequest) {
        // 判断账号是否存在  
        User pre = getById(userUpdateRequest.getId());  
        if (Objects.isNull(pre)) {  
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不存在");
        }  
        // 执行更新操作  
        User user = new User();  
        BeanUtil.copyProperties(userUpdateRequest, user);
        return ResultUtils.success(updateById(user));
    }  
}