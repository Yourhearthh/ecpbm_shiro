package com.example.controller;

import com.example.pojo.BaseResponse;
import com.example.utils.ResultCode;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 14:08
 * @version: 1.0
 */
@RestController
@Api(tags = "登录")
public class LoginController {

    @PostMapping("/login")
    public BaseResponse login(@RequestParam(value = "username") String userName,
                              @RequestParam(value = "password") String password) throws Exception {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.getSession().setAttribute("username", userName);
            currentUser.login(token);
            return BaseResponse.success(ResultCode.SUCCESS);
        } catch (UnknownAccountException e) {
            System.out.println("测试" + e.getMessage() + "账户");
            currentUser.getSession().setAttribute("msg", "账号不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println("测试" + e.getMessage() + "密码");
            currentUser.getSession().setAttribute("msg", "密码不正确");
        }
        return BaseResponse.failure(ResultCode.FAILED);

    }

    @GetMapping("/403")
    public String unauthorizedRole() {
        return "403";
    }



}
