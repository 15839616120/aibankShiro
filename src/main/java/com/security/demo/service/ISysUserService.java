package com.security.demo.service;

import com.security.demo.bean.SysUser;

import java.util.List;

public interface ISysUserService {
    SysUser getUserListByName(String name);

    List<SysUser> getUserList();

    void addUser(SysUser user);

    void updateUser(SysUser user);

    void deleteUser(SysUser user);

    void deleteUserById(String s);
}
