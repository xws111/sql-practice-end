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
@RequestMapping("/")
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
    @ApiOperation("用户注册接口")
    public BaseResponse<LoginUserVO> userRegister(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String account = userRegisterRequest.getAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(account, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
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
    @ApiOperation("用户登录接口")
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
    @ApiOperation("用户登出接口")
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
    @ApiOperation("获取当前登录用户信息接口")
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
    @ApiOperation("用户更新用户信息接口")
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

//
//    /**
//     * 新增用户
//     *
//     * @param userAddRequest 请求体
//     * @param request        servlet request
//     * @return 用户 id
//     */
//    @PostMapping("/add")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    @ApiOperation(value = "管理员新增用户接口", notes = "管理员添加新用户")
//    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
//        if (userAddRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User user = new User();
//        BeanUtils.copyProperties(userAddRequest, user);
//        boolean result = userService.save(user);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(user.getId());
//    }

//    /**
//     * 删除用户
//     *
//     * @param deleteRequest 请求体
//     * @param request       request
//     * @return 成功与否
//     */
//    @ApiOperation("管理员删除用户接口")
//    @PostMapping("/delete")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
//        if (deleteRequest == null || deleteRequest.getId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        boolean b = userService.removeById(deleteRequest.getId());
//        return ResultUtils.success(b);
//    }

//    /**
//     * 管理员更新用户信息
//     *
//     * @param userUpdateRequest 请求体
//     * @param request           request
//     * @return 成功与否
//     */
//    @ApiOperation("管理员更新用户接口")
//    @PostMapping("/update/admin")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest,
//                                            HttpServletRequest request) {
//        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User user = new User();
//        BeanUtils.copyProperties(userUpdateRequest, user);
//        boolean result = userService.updateById(user);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(true);
//    }
//
//    /**
//     * 根据 id 获取用户（仅管理员）
//     *
//     * @param id      用户 id
//     * @param request request
//     * @return 用户所有信息
//     */
//    @ApiOperation("管理员获取指定 id 用户信息接口")
//    @GetMapping("/get")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<User> getUserById(long id, HttpServletRequest request) {
//        if (id <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User user = userService.getById(id);
//        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
//        return ResultUtils.success(user);
//    }

//    /**
//     * 根据 id 获取包装类
//     *
//     * @param id      用户 id
//     * @param request request
//     * @return 用户信息 VO
//     */
//    @ApiOperation("获取用户信息 VO")
//    @GetMapping("/current")
//    public BaseResponse<UserVO> getUserVOById(long id, HttpServletRequest request) {
//        BaseResponse<User> response = getUserById(id, request);
//        User user = response.getData();
//        return ResultUtils.success(userService.getUserVO(user));
//    }
//
//    /**
//     * 分页获取用户信息（管理员）
//     *
//     * @param userQueryRequest 请求体
//     * @param request          request
//     * @return 用户信息页
//     */
//    @ApiOperation("管理员分页获取用户信息")
//    @PostMapping("/list/page")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest,
//                                                   HttpServletRequest request) {
//        long current = userQueryRequest.getCurrent();
//        long size = userQueryRequest.getPageSize();
//        Page<User> userPage = userService.page(new Page<>(current, size),
//                userService.getQueryWrapper(userQueryRequest));
//        return ResultUtils.success(userPage);
//    }

//    /**
//     * 分页获取用户封装列表
//     *
//     * @param userQueryRequest 请求体
//     * @param request          request
//     * @return 用户信息 VO 页
//     */
//    @ApiOperation("分页获取用户信息VO接口")
//    @PostMapping("/list/page/vo")
//    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest,
//                                                       HttpServletRequest request) {
//        if (userQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        long current = userQueryRequest.getCurrent();
//        long size = userQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<User> userPage = userService.page(new Page<>(current, size),
//                userService.getQueryWrapper(userQueryRequest));
//        Page<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
//        List<UserVO> userVO = userService.getUserVO(userPage.getRecords());
//        userVOPage.setRecords(userVO);
//        return ResultUtils.success(userVOPage);
//    }
//
//    /**
//     * 更新个人信息（管理员）
//     *
//     * @param userUpdateMyRequest 请求体
//     * @param request             request
//     * @return 成功与否
//     */
//    @ApiOperation("管理员更新用户信息接口")
//    @PostMapping("/admin/update")
//    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest,
//                                              HttpServletRequest request) {
//        if (userUpdateMyRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User loginUser = userService.getLoginUser(request);
//        User user = new User();
//        BeanUtils.copyProperties(userUpdateMyRequest, user);
//        user.setId(loginUser.getId());
//        boolean result = userService.updateById(user);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(true);
//    }

}
