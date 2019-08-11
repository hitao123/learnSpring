package com.example.learn.util;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import com.example.learn.annotation.RequiresPermissionsDesc;
import com.example.learn.vo.PermVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 *     Permission entity
 *     ================
 *     private RequiresPermissions requiresPermissions;
 *     private RequiresPermissionsDesc requiresPermissionsDesc; // menu [] button ""
 *     private String api;
 *
 *     =============
 *
 *     PermVo entity
 *     =============
 *     private String id;
 *     private String label;
 *     private String api;
 *     private List<PermVo> children;
 *
 */
public class PermissionUtil {

    public static List<PermVo> listPermVo(List<Permission> permissions) {
        List<PermVo> root = new ArrayList<>();
        for (Permission permission : permissions) {
            RequiresPermissions requiresPermissions = permission.getRequiresPermissions();
            RequiresPermissionsDesc requiresPermissionsDesc = permission.getRequiresPermissionsDesc();
            String api = permission.getApi();

            String[] menus = requiresPermissionsDesc.menu();
            if(menus.length != 2) {
                throw new RuntimeException("目前只支持两级菜单");
            }

            // 获取注解一级菜单
            String menu1 = menus[0];
            PermVo perm1 = null;

            for (PermVo permVo : root) {
                if (permVo.getLabel().equals(menu1)) {
                    perm1 = permVo;
                    break;
                }
            }

            if (perm1 == null) {
                perm1 = new PermVo();
                perm1.setId(menu1);
                perm1.setLabel(menu1);
                perm1.setChildren(new ArrayList<>());
                root.add(perm1);
            }

            // 获取注解二级菜单
            String menu2 = menus[1];
            PermVo perm2 = null;
            for (PermVo permVo : perm1.getChildren()) {
                if (permVo.getLabel().equals(menu2)) {
                    perm2 = permVo;
                    break;
                }
            }

            if (perm2 == null) {
                perm2 = new PermVo();
                perm2.setId(menu2);
                perm2.setLabel(menu2);
                perm2.setChildren(new ArrayList<>());
                perm1.getChildren().add(perm2);
            }

            // 获取按钮注解
            String button = requiresPermissionsDesc.button();
            PermVo leftPerm = null;
            for (PermVo permVo : perm2.getChildren()) {
                if (permVo.getChildren().equals(button)) {
                    leftPerm = permVo;
                    break;
                }
            }

            if (leftPerm == null) {
                leftPerm = new PermVo();
                leftPerm.setId(requiresPermissions.value()[0]);
                leftPerm.setLabel(requiresPermissionsDesc.button());
                leftPerm.setApi(api);
                perm2.getChildren().add(leftPerm);
            }
            else {
                throw new RuntimeException("权限已经添加，不要重复提交");
            }

        }
        return root;
    }

    public static List<Permission> listPermission(ApplicationContext context, String basicPackage) {
        Map<String, Object> map = context.getBeansWithAnnotation(Controller.class);
        List<Permission> permissions = new ArrayList<>();
        for(Map.Entry<String, Object> entry: map.entrySet()) {
            Object bean = entry.getValue();
            if(!StringUtils.contains(ClassUtils.getPackageName(bean.getClass()), basicPackage)) {
                continue;
            }
            
            Class<?> clz = bean.getClass();
            Class<?> controllerClz = clz.getSuperclass();

            RequestMapping classRequestMapping = AnnotationUtils.findAnnotation(controllerClz, RequestMapping.class);
            List<Method> methods = MethodUtils.getMethodsListWithAnnotation(controllerClz, RequiresPermissions.class);

            for(Method method: methods) {
                RequiresPermissions requiresPermissions = AnnotationUtils.getAnnotation(method, RequiresPermissions.class);
                RequiresPermissionsDesc requiresPermissionsDesc = AnnotationUtils.getAnnotation(method, RequiresPermissionsDesc.class);

                if (requiresPermissions == null || requiresPermissionsDesc == null) {
                    continue;
                }

                String api = "";
                if (classRequestMapping != null) {
                    api = classRequestMapping.value()[0];
                }

                PostMapping postMapping = AnnotationUtils.getAnnotation(method, PostMapping.class);
                if(postMapping != null) {
                    api = "POST " + api + postMapping.value()[0];

                    Permission permission = new Permission();
                    permission.setRequiresPermissions(requiresPermissions);
                    permission.setRequiresPermissionsDesc(requiresPermissionsDesc);
                    permission.setApi(api);
                    permissions.add(permission);
                    continue;
                }

                GetMapping getMapping = AnnotationUtils.getAnnotation(method, GetMapping.class);
                if(getMapping != null) {
                    api = "GET " + api + getMapping.value()[0];

                    Permission permission = new Permission();
                    permission.setRequiresPermissions(requiresPermissions);
                    permission.setRequiresPermissionsDesc(requiresPermissionsDesc);
                    permission.setApi(api);
                    permissions.add(permission);
                    continue;
                }

                throw new RuntimeException("目前权限管理应该在method的前面使用GetMapping注解或者PostMapping注解");
            }
        }
        return permissions;
    }


    public static Set<String> listPermissionString(List<Permission> permissions) {
        Set<String> permissionString = new HashSet<>();
        for(Permission permission: permissions) {
            permissionString.add(permission.getRequiresPermissions().value()[0]);
        }
        return permissionString;
    }
}
