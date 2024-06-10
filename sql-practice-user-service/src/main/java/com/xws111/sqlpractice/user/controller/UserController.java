package com.xws111.sqlpractice.user.controller;


import com.alibaba.nacos.common.utils.StringUtils;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.exception.ThrowUtils;
import com.xws111.sqlpractice.model.dto.user.UserLoginRequest;
import com.xws111.sqlpractice.model.dto.user.UserRegisterRequest;
import com.xws111.sqlpractice.model.dto.user.UserUpdateMyRequest;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.LoginUserVO;
import com.xws111.sqlpractice.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@Slf4j
@RequestMapping
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 请求体
     * @return 登录用户 VO
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册接口", notes = "用户注册接口")
    public BaseResponse<LoginUserVO> userRegister(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码不能为空！");
        }
        String account = userRegisterRequest.getAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(account, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码不能为空！");
        }
        LoginUserVO userVO = userService.userRegister(account, password, checkPassword, request);
        return ResultUtils.success(userVO);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 请求体
     * @param request          request
     * @return 用户信息 VO
     */
    @ApiOperation(value = "用户登录接口", notes = "用户登录接口")
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 参数校验
        String account = userLoginRequest.getAccount();
        String password = userLoginRequest.getPassword();
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(account, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 用户登录
        LoginUserVO loginUserVO = userService.userLogin(account, password, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 用户注销
     *
     * @param request request
     * @return 返回成功与否
     */
    @ApiOperation(value = "用户登出接口", notes = "用户登出接口")
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param request request
     * @return 用户信息 VO
     */
    @ApiOperation(value = "获取当前登录用户信息接口", notes = "获取当前登录用户信息接口")
    @GetMapping("/current")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    /**
     * 更新个人信息（用户）
     *
     * @param userUpdateMyRequest 请求体
     * @param request             request
     * @return 成功与否
     */
    @ApiOperation(value = "用户更新用户信息接口", notes = "用户更新用户信息接口")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateByUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest,
                                              HttpServletRequest request) {
        if (userUpdateMyRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateMyRequest, user);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }
}
