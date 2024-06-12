package com.xws111.sqlpractice.user.service;  
  
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xws111.sqlpractice.common.BaseResponse;
import com.xws111.sqlpractice.model.dto.user.*;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.UserVO;

/**  
 * @author Fancier  
 * @version 1.0  
 * @description: 后台管理员用户 业务层接口  
 * @date 2024/6/9 15:30  
 */public interface AdminUserService extends IService<User> {  
  
    /**  
     * 删除用户  
     * @param deleteRequest  
     * @return  
     */
    Boolean deleteUser(UserDeleteRequest deleteRequest);
  
    /**
     * 用户模糊查询 分页
     *
     * @return
     */  
    Page<UserVO> fuzzyPageQuery(UserQueryRequest userQueryRequest);
  
    /**  
     * 查询用户  
     * @param searchRequest  
     * @return  
     */  
    UserVO searchUser(UserSearchRequest searchRequest);
  
    /**  
     * 后台管理端新增用户  
     * @param addRequest  
     * @return  
     */
    Boolean addUser(UserAddRequest addRequest);
  
    /**  
     * 后台管理端 更新用户信息  
     * @param userUpdateRequest  
     * @return  
     */  
    Boolean updateUser(UserUpdateRequest userUpdateRequest);
}