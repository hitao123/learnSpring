package com.example.learn.db.service;

import com.alibaba.druid.util.StringUtils;
import com.example.learn.db.dao.MallAdminMapper;
import com.example.learn.db.entity.MallAdmin;
import com.example.learn.db.entity.MallAdmin.Column;
import com.example.learn.db.entity.MallAdminExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MallAdminService {
    private final Column[] result = new Column[] {Column.id, Column.username, Column.avatar, Column.roleIds};
    @Resource
    private MallAdminMapper adminMapper;

    public List<MallAdmin> findAdmin(String username) {
        MallAdminExample example = new MallAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    public MallAdmin findAdmin(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    public List<MallAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        MallAdminExample adminExample = new MallAdminExample();
        MallAdminExample.Criteria criteria = adminExample.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        if(!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            adminExample.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(adminExample, result);
    }

    public int updateById(MallAdmin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(MallAdmin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        admin.setLastLoginTime(LocalDateTime.now());
        adminMapper.insertSelective(admin);
    }
}
