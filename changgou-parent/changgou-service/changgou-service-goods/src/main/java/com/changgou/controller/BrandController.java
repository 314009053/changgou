package com.changgou.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result<List<Brand>> findAllBrand(){
        int x = 1/0;
        List<Brand> brands = brandService.findAll();
        return   new  Result(true, StatusCode.OK,"查询所有品牌成功！",brands);
    }

    @PostMapping("/search")
    public Result<List<Brand>> findBrand(@RequestBody Brand brand){

        List<Brand> brands = brandService.findBrand(brand);
        return   new  Result(true, StatusCode.OK,"自定义条件查询所有品牌成功！",brands);
    }

    @GetMapping(value = "/{id}")
    public Result<Brand> findBrand(@PathVariable("id") Integer  id){

        Brand brand = brandService.fingBrandByPrimaryKey(id);
        return new  Result(true, StatusCode.OK,"根据id查询品牌成功！",brand);
    }

    @PostMapping()
    public Result<Brand> add(@RequestBody Brand brand){

        brandService.addBrand(brand);
        return new  Result(true, StatusCode.OK,"新增品牌成功！");
    }

    @PostMapping("/{id}")
    public Result<Brand> update(@PathVariable("id") Integer id,@RequestBody Brand brand){
        brand.setId(id);
        brandService.updateBrand(brand);
        return new  Result(true, StatusCode.OK,"修改品牌成功！");
    }

    @DeleteMapping("/{id}")
    public Result<Brand> delete(@PathVariable("id") Integer id){

        brandService.deleteBrand(id);
        return new  Result(true, StatusCode.OK,"删除品牌成功！");
    }

    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable("page") Integer page,@PathVariable("size") Integer size){

        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return   new  Result(true, StatusCode.OK,"分页查询品牌成功！",pageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> findConditionPage(@RequestBody Brand brand,@PathVariable("page") Integer page,@PathVariable("size") Integer size){

        PageInfo<Brand> pageInfo = brandService.findConditionPage(brand,page,size);
        return   new  Result(true, StatusCode.OK,"分页查询品牌成功！",pageInfo);
    }
}
