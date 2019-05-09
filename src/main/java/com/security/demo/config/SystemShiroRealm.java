package com.security.demo.config;

import com.security.demo.bean.SysRole;
import com.security.demo.bean.SysUser;
import com.security.demo.bean.SysUserRoleKey;
import com.security.demo.service.ISysRoleService;
import com.security.demo.service.ISysUserRoleService;
import com.security.demo.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SystemShiroRealm extends AuthorizingRealm {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private ISysRoleService sysRoleService;


    /**
     * @Description: 认证：验证用户输入的账号和密码是否正确
     * @Author: wuyizhe
     * @CreateDate: 2019/5/4 22:18
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("===================执行了认证的方法==================");
        //获取用户输入的用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        char[] array = token.getPassword();
        String password = new String(array);
        //查询数据库中的用户名和密码
        SysUser user = userService.getUserListByName(username);
        if (user == null) {
            return null;
        }
        //数据库密码应当加密,而不是明文存储,暂时不做
        //boolean flag = BCrypt.checkpw(password, user.getPassword());
        //比对用户名名和密码
        boolean flag = StringUtils.equals(username, user.getUsername()) && StringUtils.equals(password, user.getPassword());
        if (flag) {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, password, this.getName());
            return authenticationInfo;
        }
        return null;
    }

    /**
     * @Description: 授权
     * @Author: wuyizhe
     * @CreateDate: 2019/5/4 22:18
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        List<String> roleNameList = Collections.EMPTY_LIST;
        if (user != null) {
            List<SysUserRoleKey> userRoleKeys = userRoleService.getRoleIdsByUserId(user.getId());
            List<String> roleIdList = userRoleKeys.stream().map(SysUserRoleKey::getRoleId).collect(Collectors.toList());
            //获取角色
            List<SysRole> roleList = sysRoleService.getRoleListByRoleIds(roleIdList);
            roleNameList = roleList.stream().map(SysRole::getRoleName).collect(Collectors.toList());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (String roleName : roleNameList) {
            info.addStringPermission(roleName);
        }
        System.out.println("===================执行了授权的方法==================");
        return info;
    }


}
