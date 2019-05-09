package com.security.demo.service;

import com.security.demo.bean.SysRole;

import java.util.List;

public interface ISysRoleService {
    List<SysRole> getRoleListByRoleIds(List<String> roleIdList);
}
