package com.security.demo.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Description: 参考 https://www.cnblogs.com/liyinfeng/p/8033869.html
 * @Author: wuyizhe
 * @CreateDate: 2019/4/27 18:07
 */
@Configuration
public class ShiroConfiguration {

    /**
     * @Description: ShiroFilterFactoryBean, 需要注入securityManager
     * @Author: wuyizhe
     * @CreateDate: 2019/5/4 12:42
     */
    @Bean
    @DependsOn("customFilter")//先注入customFilter,再注入shiroFilter
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //使用自定义的规则,按照customFilter的过滤规则执行
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("customPerms", getCustomFilter());
        shiroFilterFactoryBean.setFilters(filterMap);


        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //登陆页面,登陆失败则会回到login.html
        shiroFilterFactoryBean.setLoginUrl("/login.html");

        //登陆成功但是没有权限则跳转至此错误页面,也可以重新到登陆页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/error.html");

        //拦截器配置,1,2,3顺序不要变
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();


        // 1.放开登录接口与退出接口
        filterChainDefinitionMap.put("/login/doLogin", "anon");
        filterChainDefinitionMap.put("/login/logout", "anon");
        //放开这些静态资源
        filterChainDefinitionMap.put("/login.html", "anon");
        filterChainDefinitionMap.put("/*.js", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");

        // 2.默认过滤,配置了此项,在访问admin与normal文件夹下面的html页面时,会触发SysShiroRealm类的授权方法
        // 只有具备abc权限,才能访问admin文件夹下面的html页面
        // 相当于给资源上一把锁
        // 而SysShiroRealm类的授权方法则可以理解为拿到这一把锁的钥匙
        // 此处用角色代替权限更为方便
        // 允许ROLE_ADMIN角色访问admin文件夹下面的html页面
        filterChainDefinitionMap.put("/admin/**", "customPerms[ROLE_ADMIN,ROLE_SUPER]");
        // 允许ROLE_USER角色访问admin文件夹下面的html页面
        filterChainDefinitionMap.put("/normal/**", "customPerms[ROLE_USER,ROLE_SUPER]");

        // 3.需要认证
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * @Description: 安检管理器, 需要注入SystemShiroRealm
     * @Author: wuyizhe
     * @CreateDate: 2019/5/4 12:43
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getSystemShiroRealm());
        return securityManager;
    }


    @Bean
    public SystemShiroRealm getSystemShiroRealm() {
        SystemShiroRealm systemShiroRealm = new SystemShiroRealm();
        return systemShiroRealm;
    }

    @Bean
    public CustomFilter getCustomFilter() {
        CustomFilter customFilter = new CustomFilter();
        return customFilter;
    }

    /**
     * @Description: 开启Shiro的注解
     * 配置以下两个bean:DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor
     * @Author: wuyizhe
     * @CreateDate: 2019/5/4 12:45
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        //注意加上此注解,因为此处动态代理默认使用的是JDK动态代理,如果被代理的当前类没有实现任何接口,则采用cglib派生子类的代理方式
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * @Description: 开启aop注解支持,不加入这个注解不生效
     * @Author: wuyizhe
     * @CreateDate: 2019/5/4 12:46
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
