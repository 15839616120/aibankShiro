package com.security.demo.controller;


import com.security.demo.bean.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("login")
public class LoginController {

    @ResponseBody
    @PostMapping(value = "/doLogin")
    public Boolean login(@RequestBody SysUser user){
        Boolean flag = false;
        try {

            //可以从shiro中获取当前登陆人
            //User principal = (User) SecurityUtils.getSubject().getPrincipal();

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

            //去执行安全管理器,安全管理器再去执行SystemShiroRealm类的认证方法
            subject.login(token);
            flag = true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @ResponseBody
    @PostMapping("logout")
    public boolean logout(){
        SecurityUtils.getSubject().logout();
        return true;
    }

}
