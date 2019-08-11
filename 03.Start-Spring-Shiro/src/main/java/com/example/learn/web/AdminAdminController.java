package com.example.learn.web;

import com.example.learn.annotation.RequiresPermissionsDesc;
import com.example.learn.db.entity.MallAdmin;
import com.example.learn.db.service.MallAdminService;
import com.example.learn.util.BCryptPasswordEncoder;
import com.example.learn.util.ResponseUtil;
import com.github.pagehelper.PageInfo;
import com.sun.istack.internal.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/admin")
@Validated
public class AdminAdminController {

    private static final Log log = LogFactory.getLog(AdminAdminController.class);

    @Autowired
    private MallAdminService adminService;

    @RequiresPermissions("admin:admin:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String username,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order) {
        List<MallAdmin> adminList = adminService.querySelective(username, page, limit, sort, order);
        long total = PageInfo.of(adminList).getTotal();

        Map<String, Object> data = new HashMap<>();
        data.put("items", adminList);
        data.put("total", total);
        return ResponseUtil.ok(data);
    }

    @RequiresPermissions("admin:admin:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "创建")
    @GetMapping("/create")
    public Object create(@RequestBody MallAdmin admin) {
        String username = admin.getUsername();
        List<MallAdmin> adminList = adminService.findAdmin(username);

        if (adminList.size() > 0) {
            return ResponseUtil.fail(1001, "管理员已经存在");
        }

        String password = admin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        admin.setPassword(encodePassword);
        adminService.add(admin);

        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:admin:read")
    @RequiresPermissionsDesc(menu = {"系统管理", "管理员管理"}, button = "详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        MallAdmin admin = adminService.findAdmin(id);
        return ResponseUtil.ok(admin);
    }

    public Object delete(@RequestBody MallAdmin admin) {
        Integer otherAdminId = admin.getId();
        if (otherAdminId == null) {
            return ResponseUtil.badArgument();
        }

        Subject currentUser = SecurityUtils.getSubject();
        MallAdmin currentAdmin = (MallAdmin) currentUser.getPrincipal();

        if (currentAdmin.getId().equals(otherAdminId)) {
            return ResponseUtil.fail(1003, "管理员不能删除自己的账号");
        }

        adminService.deleteById(otherAdminId);
        return ResponseUtil.ok();
    }
}
