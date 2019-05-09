package com.security.demo.service.impl;

import com.security.demo.bean.SysUser;
import com.security.demo.bean.SysUserExample;
import com.security.demo.dao.SysUserMapper;
import com.security.demo.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserListByName(String name) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(name);
        List<SysUser> userList = sysUserMapper.selectByExample(example);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
    }

    @Override
    public List<SysUser> getUserList() {
        SysUserExample example = new SysUserExample();
        List<SysUser> userList = sysUserMapper.selectByExample(example);
        return userList;
    }

    @Override
    public void addUser(SysUser user) {
        sysUserMapper.insert(user);
    }

    @Override
    public void updateUser(SysUser user) {
        sysUserMapper.updateByPrimaryKey(user);
    }

    @Override
    public void deleteUser(SysUser user) {
        sysUserMapper.deleteByPrimaryKey(user.getId());
    }

    @Override
    public void deleteUserById(String s) {
        sysUserMapper.deleteByPrimaryKey(s);
    }
}
