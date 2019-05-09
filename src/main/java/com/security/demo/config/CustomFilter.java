package com.security.demo.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义过滤器(role)
 * 作用是filterChainDefinitionMap.put("/admin/**", "customPerms[ROLE_ADMIN,ROLE_SUPER]"),
 * 这种写法按照shiro默认规则是并且的关系,只有同时具备了两个,才能获得访问权限
 * 自定义过滤器就是让这种写法变成变成或的关系,而不是并的关系,满足一项即可
 */
@Component
public class CustomFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object object) throws Exception {


        Subject subject = getSubject(servletRequest, servletResponse);
        String[] perms = (String[]) object;
        if (ArrayUtils.isEmpty(perms)) {
            return true;
        }

        for (String perm : perms) {
            if (subject.isPermitted(perm)) {
                return true;
            }
        }
        return false;
    }
}
