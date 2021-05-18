package com.example.controller;

import com.example.pojo.BaseResponse;
import com.example.utils.ResultCode;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 15:51
 * @version: 1.0
 */
@RestController
@Api(tags = "注销")
public class LogoutController {
    @GetMapping(value = "/logout")
    public BaseResponse logout(HttpServletResponse response) throws IOException {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
            return BaseResponse.success(ResultCode.SUCCESS);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        return BaseResponse.failure(ResultCode.FAILED);
    }


}
