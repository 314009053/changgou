package com.changgou.service.impl;


import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {


    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public List<Brand> findBrand(Brand brand) {
        Example example = getExample(brand);
        return brandMapper.selectByExample(example);

    }

    public Example getExample(Brand brand){
        //自定义条件搜索对象
        Example example = new Example(Brand.class);
        //条件构造器
        Example.Criteria criteria = example.createCriteria();
        if(brand != null){
            //条件一：根据名称模糊查询
            if(!StringUtils.isEmpty(brand.getName())){
                //参数一：实体类的字段名，参数二：实体类的字段值
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            //查询条件二：首字母等于某个值
            if(!StringUtils.isEmpty(brand.getLetter())){
                //参数一：实体类的字段名，参数二：实体类的字段值
                criteria.andEqualTo("letter",brand.getLetter());
            }

            return  example;
        }else  return null;
    }

    @Override
    public Brand fingBrandByPrimaryKey(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * insertSelective,updateByPrimaryKeySelective()
     * 带Selective方法会自动忽略空值
     * @param brand 品牌
     */
    @Override
    public void addBrand(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void deleteBrand(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 分页实现：  PageHelper.startPage(page,size);分页实现后面紧跟几个查询
     * @param page 当前页
     * @param size 每页显示条数
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        //集合查询,此时已完成分页，只查询范围内的所有集合
        List<Brand> brands = brandMapper.selectAll();


        //封装返回值PageIfo
        return new PageInfo<Brand>(brands);
    }

    @Override
    public PageInfo<Brand> findConditionPage(Brand brand, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        Example example = getExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        return new PageInfo(brands);

    }


}
