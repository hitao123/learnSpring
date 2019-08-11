package com.example.learn.web;

import com.example.learn.annotation.RequiresPermissionsDesc;
import com.example.learn.db.entity.MallRole;
import com.example.learn.db.service.MallAdminService;
import com.example.learn.db.service.MallPermissionService;
import com.example.learn.db.service.MallRoleService;
import com.example.learn.util.ResponseUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/role")
@Validated
public class AdminRoleController {
    @Autowired
    private MallRoleService roleService;
    @Autowired
    private MallPermissionService permissionService;
    @Autowired
    private MallAdminService adminService;

    @RequiresPermissions("admin:role:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "角色查询")
    @GetMapping("/list")
    public Object list(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order) {
        List<MallRole> roleList = roleService.querySelective(name, page, limit, sort, order);
        Long total = PageInfo.of(roleList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", roleList);

        return ResponseUtil.ok(data);
    }
}
