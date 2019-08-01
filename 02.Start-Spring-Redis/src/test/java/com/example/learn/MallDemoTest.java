package com.example.learn;

import com.example.learn.db.entity.MallBrand;
import com.example.learn.db.service.MallBrandService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MallDemoTest {

    @Autowired
    private MallBrandService brandService;

    @Test
    public void test() {
        List<MallBrand> mallBrandList = brandService.all();
        Assert.assertTrue(mallBrandList.size() != 0);
    }
}
