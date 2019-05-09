package com.security.demo.service.impl;

import com.security.demo.bean.*;
import com.security.demo.dao.SysPermissionMapper;
import com.security.demo.service.ISysPermissionService;
import com.security.demo.service.ISysRolePermissionService;
import com.security.demo.service.ISysUserRoleService;
import com.security.demo.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRolePermissionService sysRolePermissionService;
    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> getUserPermissionTree() {

        SysUser principal = (SysUser) SecurityUtils.getSubject().getPrincipal();
        String name = StringUtils.EMPTY;
        if(principal != null){
            name = principal.getUsername();
        }


        //查询用户
        SysUser sysUser = sysUserService.getUserListByName(name);
        //查询用户角色roleIds
        List<SysUserRoleKey> sysUserRoleKeys = sysUserRoleService.getRoleIdsByUserId(sysUser.getId());
        List<String> roleIds = sysUserRoleKeys.stream().map(SysUserRoleKey::getRoleId).collect(Collectors.toList());
        //根据roleIds查询用户拥有的所有权限
        List<SysRolePermissionKey> sysRolePermissionKeys = sysRolePermissionService.getPermissionIdsByRoleIds(roleIds);
        List<String> permissionIds = sysRolePermissionKeys.stream().map(SysRolePermissionKey::getPermissionId).collect(Collectors.toList());
        List<SysPermission> allPermissions = sysPermissionService.getPermissionListByPermissionIds(permissionIds);
        //这里的allPermissions权限也许会有重复，那么就需要重写SysPermission类的equals方法和hashCode方法,根据id去重即可
        List<SysPermission> allPermissionList = allPermissions.stream().distinct().collect(Collectors.toList());

        //考虑到页面可能是多级菜单，一级菜单下面有二级菜单，二级菜单下面有三级菜单等等，
        // 但是allPermissionList只是查出所有菜单，并未分级，因此我们需要把它整理成树状的数据结构
        // 需要再SysPermission中配置一个子权限菜单集合，参见SysPermission类
        //我们从顶级父权限菜单也就是pid为0的开始整理
        List<SysPermission> permissionList = allPermissionList.stream().filter(sysPermission ->
                                                StringUtils.equals(sysPermission.getPid(), NumberUtils.INTEGER_ZERO.toString()))
                                                .collect(Collectors.toList());

        //给每一个父菜单构建菜单树
        for(SysPermission permission : permissionList){
            this.getPermissionTree(allPermissionList, permission);
        }
        return permissionList;
    }


    public void getPermissionTree(List<SysPermission> allPermissionList, SysPermission permission){

        List<SysPermission> childPermissionList = new ArrayList<>();
        for (SysPermission sysPermission : allPermissionList) {

            // 如果当前遍历得到的菜单的父id与传入的父菜单id一样,则说明:
            // 当前遍历得到的菜单属于此父菜单,就把它加入到子菜单集合childPermissionList
            // 最后设置到传入的父菜单permission上,
            // 递归调用此方法,最终构建出一个树形结构
            if(StringUtils.equals(sysPermission.getPid(), permission.getId())){
                childPermissionList.add(sysPermission);
                //还是因为页面可能是多级菜单，所以子菜单sysPermission下面还有可能存在子菜单，此处需要递归调用
                this.getPermissionTree(allPermissionList, sysPermission);
            }
        }
        permission.setChildren(childPermissionList);

    }

    @Override
    public List<SysPermission> getPermissionListByPermissionIds(List<String> permissionIds) {
        SysPermissionExample example = new SysPermissionExample();
        example.createCriteria().andIdIn(permissionIds);
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectByExample(example);
        return CollectionUtils.isEmpty(sysPermissionList) ? Collections.EMPTY_LIST : sysPermissionList;
    }
}
