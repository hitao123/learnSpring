package com.example.learn.shiro;

import com.example.learn.db.entity.MallAdmin;
import com.example.learn.util.BCryptPasswordEncoder;
import org.apache.shiro.authc.*;
import com.alibaba.druid.util.StringUtils;
import com.example.learn.db.service.MallAdminService;
import com.example.learn.db.service.MallPermissionService;
import com.example.learn.db.service.MallRoleService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;


public class AdminAuthorizingRealm extends AuthorizingRealm {


    private static  final Logger log = LoggerFactory.getLogger(AdminAuthorizingRealm.class);
    @Autowired
    private MallAdminService adminService;
    @Autowired
    private MallRoleService roleService;
    @Autowired
    private MallPermissionService permissionService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection can not be null");
        }

        MallAdmin admin = (MallAdmin) getAvailablePrincipal(principals);
        Integer [] roleIds = admin.getRoleIds();
        Set<String> roles = roleService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByRoleIds(roleIds);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        if (StringUtils.isEmpty(username)) {
            throw new AccountException("用户名不能为空");
        }

        if (StringUtils.isEmpty((password))) {
            throw new AccountException("用户密码不能为空");
        }

        List<MallAdmin> adminList = adminService.findAdmin(username);

        if (adminList.size() == 0) {
            throw new UnknownAccountException("找不到" + username + "的信息");
        }

        MallAdmin admin = adminList.get(0);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, admin.getPassword())) {
            throw new UnknownAccountException("找不到" + username + "的信息");
        }

        return new SimpleAuthenticationInfo(admin, password, getName());
    }

}
