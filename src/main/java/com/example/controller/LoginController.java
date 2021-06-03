package com.example.controller;

import com.example.jwt.JwtUtils;
import com.example.pojo.BaseResponse;
import com.example.utils.ResultCode;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


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
                              @RequestParam(value = "password") String password, ServletResponse response) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        boolean loginSuccess = false;
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            subject.login(token);
            subject.login(token);
            loginSuccess = true;
        } catch (UnknownAccountException e) {
            System.out.println("测试" + e.getMessage() + "账户");
            subject.getSession().setAttribute("msg", "账号不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println("测试" + e.getMessage() + "密码");
            subject.getSession().setAttribute("msg", "密码不正确");
        }
        if (loginSuccess) {
            // 若登录成功，签发 JWT token
            String jwtToken = JwtUtils.sign(userName, JwtUtils.SECRET);
            // 将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
            ((HttpServletResponse) response).setHeader(JwtUtils.AUTH_HEADER, jwtToken);
            return BaseResponse.success(ResultCode.SUCCESS);
        }
        return BaseResponse.failure(ResultCode.FAILED);

    }




}
