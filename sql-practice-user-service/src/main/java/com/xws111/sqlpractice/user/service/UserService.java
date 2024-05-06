package com.xws111.sqlpractice.user.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xws111.sqlpractice.model.dto.user.UserQueryRequest;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.LoginUserVO;
import com.xws111.sqlpractice.model.vo.UserVO;
import javax.servlet.http.HttpServletRequest;

import java.util.List;


/**
* @author xg
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-05-03 22:04:52
*/
public interface UserService extends IService<User> {

    String USER_LOGIN_STATE = "loginState";
    /**
     * 用户注册
     *
     * @param account       用户账户
     * @param password      用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String account, String password, String checkPassword);

    /**
     * 用户登录
     *
     * @param account  用户账户
     * @param password 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String account, String password, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    Wrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
