package com.changgou.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {

    /**
     * 查询所有品牌
     * @return
     */
    List<Brand> findAll();
    /**
     * 自定义条件查询品牌
     * @return
     */
    List<Brand> findBrand(Brand brand);
    /**
     * 根据id查询品牌
     *
     */
    Brand  fingBrandByPrimaryKey(Integer id);

    /**
     * 新增品牌
     */
    void addBrand(Brand brand);

    /**
     * 修改品牌
     */
    void updateBrand(Brand brand);

    /**
     * 根据id删除品牌
     */
    void deleteBrand(Integer id);

    /**
     * 分页查询
     */
    PageInfo<Brand> findPage(Integer page, Integer size);

    /**
     * 条件+分页查询
     */
    PageInfo<Brand> findConditionPage(Brand brand,Integer page, Integer size);
}
