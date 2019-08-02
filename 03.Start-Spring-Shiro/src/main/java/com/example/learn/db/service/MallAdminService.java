package com.example.learn.db.service;

import com.example.learn.db.dao.MallAdminMapper;
import com.example.learn.db.entity.MallAdmin;
import com.example.learn.db.entity.MallAdminExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MallAdminService {
    @Resource
    private MallAdminMapper adminMapper;

    public List<MallAdmin> findAdmin(String username) {
        MallAdminExample example = new MallAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    public int updateById(MallAdmin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.logicalDeleteByPrimaryKey(id);
    }
}
