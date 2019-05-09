package com.security.demo.dao;

import com.security.demo.bean.SysRolePermissionExample;
import com.security.demo.bean.SysRolePermissionKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRolePermissionMapper {
    long countByExample(SysRolePermissionExample example);

    int deleteByExample(SysRolePermissionExample example);

    int deleteByPrimaryKey(SysRolePermissionKey key);

    int insert(SysRolePermissionKey record);

    int insertSelective(SysRolePermissionKey record);

    List<SysRolePermissionKey> selectByExample(SysRolePermissionExample example);

    int updateByExampleSelective(@Param("record") SysRolePermissionKey record, @Param("example") SysRolePermissionExample example);

    int updateByExample(@Param("record") SysRolePermissionKey record, @Param("example") SysRolePermissionExample example);
}