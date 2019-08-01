package com.example.learn.db.service;

import com.example.learn.db.dao.MallRoleMapper;
import com.example.learn.db.entity.MallRole;
import com.example.learn.db.entity.MallRoleExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MallRoleService {
    @Resource
    private MallRoleMapper roleMapper;


    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> roles = new HashSet<String>();
        if(roleIds.length == 0){
            return roles;
        }

        MallRoleExample example = new MallRoleExample();
        example.or().andIdIn(Arrays.asList(roleIds)).andEnabledEqualTo(true).andDeletedEqualTo(false);
        List<MallRole> roleList = roleMapper.selectByExample(example);

        for(MallRole role : roleList){
            roles.add(role.getName());
        }

        return roles;
    }
}
