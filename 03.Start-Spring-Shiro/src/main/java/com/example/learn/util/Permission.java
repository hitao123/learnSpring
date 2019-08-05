package com.example.learn.util;

import com.example.learn.annotation.RequiresPermissionsDesc;
import org.apache.shiro.authz.annotation.RequiresPermissions;

public class Permission {
    public RequiresPermissions getRequiresPermissions() {
        return requiresPermissions;
    }

    public void setRequiresPermissions(RequiresPermissions requiresPermissions) {
        this.requiresPermissions = requiresPermissions;
    }

    public RequiresPermissionsDesc getRequiresPermissionsDesc() {
        return requiresPermissionsDesc;
    }

    public void setRequiresPermissionsDesc(RequiresPermissionsDesc requiresPermissionsDesc) {
        this.requiresPermissionsDesc = requiresPermissionsDesc;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    private RequiresPermissions requiresPermissions;
    private RequiresPermissionsDesc requiresPermissionsDesc;
    private String api;

}
