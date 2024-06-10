package com.xws111.sqlpractice.user.controller;  
  
import com.fasterxml.jackson.databind.ser.Serializers;  
import com.xws111.sqlpractice.common.BaseResponse;  
import com.xws111.sqlpractice.common.PageRequest;  
import com.xws111.sqlpractice.common.PageResponse;  
import com.xws111.sqlpractice.model.dto.user.*;  
import com.xws111.sqlpractice.user.service.AdminUserService;  
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
 */@RestController  
@RequestMapping("/admin")  
@RequiredArgsConstructor  
public class AdminUserController {  
    private final AdminUserService adminUserService;  
  
    @PostMapping("/delete")  
    public BaseResponse deleteUser(@RequestBody UserDeleteRequest deleteRequest) {
        return adminUserService.deleteUser(deleteRequest);  
    }  
  
    @PostMapping("/list/page")  
    public PageResponse FuzzyPageQuery(@RequestBody UserQueryRequest userQueryRequest) {
        return adminUserService.fuzzyPageQuery(userQueryRequest);  
    }  
  
    @PostMapping("/search")  
    public BaseResponse searchUser(@RequestBody UserSearchRequest searchRequest) {
        return adminUserService.searchUser(searchRequest);  
    }  
  
    @PostMapping("/add")  
    public BaseResponse addUser(@RequestBody UserAddRequest addRequest) {
        return adminUserService.addUser(addRequest);  
    }  
  
    @PostMapping("/update")  
    public BaseResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return adminUserService.updateUser(userUpdateRequest);  
    }  
}