package com.example.learn.web;

import com.example.learn.annotation.RequiresPermissionsDesc;
import com.example.learn.db.entity.MallAdmin;
import com.example.learn.db.service.MallAdminService;
import com.example.learn.util.ResponseUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
}
