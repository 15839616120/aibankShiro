package com.security.demo.service;

import com.security.demo.bean.SysPermission;

import java.util.List;

public interface ISysPermissionService {

    List<SysPermission> getUserPermissionTree();

    List<SysPermission> getPermissionListByPermissionIds(List<String> permissionIds);
}
