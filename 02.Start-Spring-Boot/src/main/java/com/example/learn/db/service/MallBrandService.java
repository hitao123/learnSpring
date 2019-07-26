package com.example.learn.db.service;

import com.example.learn.db.dao.MallBrandMapper;
import com.example.learn.db.entity.MallBrand;
import com.example.learn.db.entity.MallBrandExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MallBrandService {
    @Resource
    private MallBrandMapper brandMapper;

    public void add(MallBrand brand) {
        brand.setAddTime(LocalDateTime.now());
        brand.setUpdateTime(LocalDateTime.now());
        brandMapper.insertSelective(brand);
    }

    public void deleteById(Integer id) {
        brandMapper.logicalDeleteByPrimaryKey(id);
    }

    public MallBrand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public int updateById(MallBrand brand) {
        brand.setUpdateTime(LocalDateTime.now());
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    public int queryTotalCount() {
        MallBrandExample example = new MallBrandExample();
        example.or().andDeletedEqualTo(false);
        return (int) brandMapper.countByExample(example);
    }

    public List<MallBrand> all() {
        MallBrandExample example = new MallBrandExample();
        example.or().andDeletedEqualTo(false);
        return brandMapper.selectByExample(example);
    }
}
