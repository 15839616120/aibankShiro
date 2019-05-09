package com.security.demo.service.impl;

import com.security.demo.bean.SysRolePermissionExample;
import com.security.demo.bean.SysRolePermissionKey;
import com.security.demo.dao.SysRolePermissionMapper;
import com.security.demo.service.ISysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class SysRolePermissionServiceImpl implements ISysRolePermissionService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRolePermissionKey> getPermissionIdsByRoleIds(List<String> roleIds) {

        SysRolePermissionExample example = new SysRolePermissionExample();
        example.createCriteria().andRoleIdIn(roleIds);
        List<SysRolePermissionKey> sysRolePermissionKeys = sysRolePermissionMapper.selectByExample(example);
        return CollectionUtils.isEmpty(sysRolePermissionKeys) ? Collections.EMPTY_LIST : sysRolePermissionKeys;
    }

}
