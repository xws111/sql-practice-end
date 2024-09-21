package com.xws111.sqlpractice.security.aop;

import com.xws111.sqlpractice.common.ErrorCode;
import com.xws111.sqlpractice.exception.BusinessException;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.service.UserFeignClient;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoginCheckAspect {
    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private HttpServletRequest request;


    @Pointcut("@annotation(com.xws111.sqlpractice.security.annotation.LoginCheck)")
    private void cutMethod() {

    }

    @Before("cutMethod()")
    public void loginCheck() {
        User loginUser = (User) request.getSession().getAttribute(UserFeignClient.USER_LOGIN_STATE);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
    }
}
