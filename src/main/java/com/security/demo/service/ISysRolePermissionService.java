package com.security.demo.service;

import com.security.demo.bean.SysRolePermissionKey;

import java.util.List;

public interface ISysRolePermissionService {
    List<SysRolePermissionKey> getPermissionIdsByRoleIds(List<String> roleIds);
}
