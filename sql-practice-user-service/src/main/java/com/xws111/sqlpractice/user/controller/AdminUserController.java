package com.xws111.sqlpractice.user.controller;  
  

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.common.ResultUtils;
import com.xws111.sqlpractice.model.dto.user.*;
import com.xws111.sqlpractice.model.vo.UserVO;
import com.xws111.sqlpractice.user.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  
  
/**  
 * @author Fancier  
 * @version 1.0  
 * @description: 后台管理员用户 控制层  
 * @date 2024/6/9 15:25  
 */
@RestController
@RequestMapping("/admin")  
@RequiredArgsConstructor
@Api(tags = "管理端用户控制层")
public class AdminUserController {  
    private final AdminUserService adminUserService;  
  
    @PostMapping("/delete")
    @ApiOperation(value = "deleteUser", notes = "根据id删除用户")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeleteRequest deleteRequest) {
        Boolean isActive = adminUserService.deleteUser(deleteRequest);
        return ResultUtils.success(isActive);
    }  
  
    @PostMapping("/list/page")
    @ApiOperation(value = "fuzzyPageQuery",notes = "管理员分页获取用户信息")
    public BaseResponse<Page<UserVO>> fuzzyPageQuery(@RequestBody UserQueryRequest userQueryRequest) {
        Page<UserVO> page = adminUserService.fuzzyPageQuery(userQueryRequest);
        return ResultUtils.success(page);
    }  
  
    @PostMapping("/search")
    @ApiOperation(value = "searchUser",notes = "管理员根据id查询用户")
    public BaseResponse<UserVO> searchUser(@RequestBody UserSearchRequest searchRequest) {
        UserVO userVO = adminUserService.searchUser(searchRequest);
        return ResultUtils.success(userVO);
    }  
  
    @PostMapping("/add")
    @ApiOperation(value = "addUser", notes = "管理端添加用户")
    public BaseResponse<Boolean> addUser(@RequestBody UserAddRequest addRequest) {
        Boolean isTure = adminUserService.addUser(addRequest);
        return ResultUtils.success(isTure);
    }  
  
    @PostMapping("/update")
    @ApiOperation(value = "updateUser", notes = "管理端更新用户信息")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        Boolean isActive = adminUserService.updateUser(userUpdateRequest);
        return ResultUtils.success(isActive);
    }  
}