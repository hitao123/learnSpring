package com.example.learn.db.service;

import com.alibaba.druid.util.StringUtils;
import com.example.learn.db.dao.MallRoleMapper;
import com.example.learn.db.entity.MallRole;
import com.example.learn.db.entity.MallRoleExample;
import com.github.pagehelper.PageHelper;
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

    public List<MallRole> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        MallRoleExample roleExample = new MallRoleExample();
        MallRoleExample.Criteria criteria = roleExample.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andNameLike("%" + username + "%");
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            roleExample.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return roleMapper.selectByExample(roleExample);
    }
}
