package com.shop.api.brand.controller;

import com.shop.api.brand.biz.IBrandService;
import com.shop.api.brand.po.Brand;
import com.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Resource(name = "brandService")
    private IBrandService brandService;


    @PostMapping
    public ServerResponse add(Brand brand){
        return brandService.addBrand(brand);
    }


    @GetMapping
    public ServerResponse list(){
          return brandService.findList();
    }

    @DeleteMapping("/{id}")
    public ServerResponse delete(@PathVariable Integer id){
        return brandService.delete(id);
    }

    @PutMapping
    public ServerResponse update(@RequestBody Brand brand){
        return brandService.update(brand);
    }


    @DeleteMapping
    public ServerResponse deleteBatch(String ids){
        return brandService.deleteBatch(ids);
    }






}
