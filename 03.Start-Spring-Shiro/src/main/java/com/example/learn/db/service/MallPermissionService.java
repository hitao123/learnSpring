package com.example.learn.db.service;

import com.example.learn.db.dao.MallPermissionMapper;
import com.example.learn.db.entity.MallPermission;
import com.example.learn.db.entity.MallPermissionExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MallPermissionService {

    @Resource
    private MallPermissionMapper permissionMapper;

    public Set<String> queryByRoleIds(Integer[] roleIds) {
        Set<String> permissions = new HashSet<String>();
        if(roleIds.length == 0){
            return permissions;
        }

        MallPermissionExample example = new MallPermissionExample();
        example.or().andRoleIdIn(Arrays.asList(roleIds)).andDeletedEqualTo(false);
        List<MallPermission> permissionList = permissionMapper.selectByExample(example);

        for(MallPermission permission : permissionList){
            permissions.add(permission.getPermission());
        }

        return permissions;
    }
}
