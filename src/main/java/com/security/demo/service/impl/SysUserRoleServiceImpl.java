package com.security.demo.service.impl;

import com.security.demo.bean.SysUserRoleExample;
import com.security.demo.bean.SysUserRoleKey;
import com.security.demo.dao.SysUserRoleMapper;
import com.security.demo.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserRoleKey> getRoleIdsByUserId(String userId) {

        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<SysUserRoleKey> sysUserRoleKeys = sysUserRoleMapper.selectByExample(example);
        return CollectionUtils.isEmpty(sysUserRoleKeys) ? Collections.EMPTY_LIST : sysUserRoleKeys;
    }
}
