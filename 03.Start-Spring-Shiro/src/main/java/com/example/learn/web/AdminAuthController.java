package com.example.learn.web;

import com.alibaba.druid.util.StringUtils;
import com.example.learn.db.entity.MallAdmin;
import com.example.learn.db.service.MallAdminService;
import com.example.learn.db.service.MallPermissionService;
import com.example.learn.db.service.MallRoleService;
import com.example.learn.util.JacksonUtil;
import com.example.learn.util.ResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {

    private final Log logger = LogFactory.getLog(AdminAuthController.class);

    @Autowired
    private MallAdminService adminService;
    @Autowired
    private MallRoleService roleService;
    @Autowired
    private MallPermissionService permissionService;

    @PostMapping("/login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");

        logger.info("username=" + username + " password=" + password);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResponseUtil.badArgument();
        }

        Subject currentUser = SecurityUtils.getSubject();

        try {
            currentUser.login(new UsernamePasswordToken(username, password));

        } catch (UnknownAccountException uae) {
            return ResponseUtil.fail(1000, "用户账户或者密码错误");
        } catch (LockedAccountException lae) {
            return ResponseUtil.fail(1000, "用户账户已经锁定");
        } catch (AuthenticationException ace) {
            return ResponseUtil.fail(1000, "认证失败");
        }

        currentUser = SecurityUtils.getSubject();
        MallAdmin admin = (MallAdmin) currentUser.getPrincipal();
        admin.setLastLoginTime(LocalDateTime.now());
        adminService.updateById(admin);
        return ResponseUtil.ok(currentUser.getSession().getId());
    }

    @RequiresAuthentication
    @PostMapping("/loginout")
    public Object logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ResponseUtil.ok();
    }

    @GetMapping("/info")
    public Object info() {
        logger.info("🐂🐂============================================>");
        Subject currentUser = SecurityUtils.getSubject();
        MallAdmin admin = (MallAdmin) currentUser.getPrincipal();

        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        Integer[] roleIds = admin.getRoleIds();
        Set<String> roles = roleService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByRoleIds(roleIds);

        data.put("roles", roles);

        return ResponseUtil.ok(data);

    }

    @GetMapping("/401")
    public Object page401() {
        return ResponseUtil.unlogin();
    }

    @GetMapping("/index")
    public Object pageindex() {
        return ResponseUtil.ok();
    }

    @GetMapping("/403")
    public Object page403() {
        return ResponseUtil.unauthz();
    }

}
