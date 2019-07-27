package com.example.learn.web;

import com.example.learn.db.entity.MallBrand;
import com.example.learn.db.service.MallBrandService;
import com.example.learn.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin/ad")
public class BrandController {
    @Autowired
    private MallBrandService brandService;

    @GetMapping("/brand")
    public Object list() {
        Map<String, Object> data = new HashMap<>();

        List<MallBrand> brandList = brandService.all();
        data.put("items", brandList);
        return ResponseUtil.ok(data);
    }
}
