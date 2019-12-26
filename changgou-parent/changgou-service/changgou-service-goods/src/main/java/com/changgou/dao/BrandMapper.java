package com.changgou.dao;

import com.changgou.goods.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 通用mapper  需要继承tk的mapper
 * 增：Mapper.insert()     Mapper.insertSelective()
 * 查：Mapper.select(T)     Mapper.selectByPrimaryKey(ID)
 * 改: Mapper.update(T)      Mapper.updateByPrimaryKey(T)
 *
 */


public interface BrandMapper extends Mapper<Brand> {
}
