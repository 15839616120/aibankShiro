package com.security.demo.service.impl;

import com.security.demo.bean.SysRole;
import com.security.demo.bean.SysRoleExample;
import com.security.demo.dao.SysRoleMapper;
import com.security.demo.service.ISysRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> getRoleListByRoleIds(List<String> roleIdList) {

        SysRoleExample example = new SysRoleExample();
        example.createCriteria().andIdIn(roleIdList);
        List<SysRole> roleList = sysRoleMapper.selectByExample(example);
        return CollectionUtils.isEmpty(roleList) ? Collections.emptyList() : roleList;
    }
}
