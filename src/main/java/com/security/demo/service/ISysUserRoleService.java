package com.security.demo.service;

import com.security.demo.bean.SysUserRoleKey;

import java.util.List;

public interface ISysUserRoleService {
    List<SysUserRoleKey> getRoleIdsByUserId(String userId);
}
