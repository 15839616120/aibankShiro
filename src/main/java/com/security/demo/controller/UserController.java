package com.security.demo.controller;

import com.security.demo.bean.SysPermission;
import com.security.demo.bean.SysUser;
import com.security.demo.service.ISysPermissionService;
import com.security.demo.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysPermissionService sysPermissionService;

    @ResponseBody
    @RequestMapping("getUserList")
    public List<SysUser> getUserList(){
        List<SysUser> userList = sysUserService.getUserList();
        return userList;
    }


    @ResponseBody
    @RequestMapping("getUserPermissionTree")
    public List<SysPermission> getUserPermissionTree(){
        List<SysPermission> permissionList = sysPermissionService.getUserPermissionTree();
        return permissionList;
    }


    /**
     * 说明:
     * 此处用来做shiro的方法级别控制,也叫细颗粒
     * 像deleteUserById或者修改等敏感方法,权限只授予超级管理员,其他角色均无权执行
     * 此时就需要方法级别的细颗粒
     * 具体做法参见方法
     * @return
     */
    @RequiresPermissions("ROLE_SUPER")
    @ResponseBody
    @RequestMapping("deleteUserById")
    public boolean deleteUserById(){
        sysUserService.deleteUserById("531391d3-7b9b-4287-ae23-67c009fd4bfa");
        return true;
    }

}
