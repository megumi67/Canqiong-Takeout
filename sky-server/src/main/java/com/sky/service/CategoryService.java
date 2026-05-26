package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface CategoryService {

    /**
     * 插入数据
     * @param category
     */
    void add(CategoryDTO category);


    /**
     * 删除分类
     * @param id
     */
    void delete(Long id);

    /**
     * 启用/禁用分类
     * @param id
     * @param status
     */
    void OpenOrNot(Long id, Integer status);


    /**
     * 分页分类查询
     * @param categoryPageQueryDTO
     */
    PageResult selectByPages(CategoryPageQueryDTO categoryPageQueryDTO);


    /**
     *修改分类
     * @param categoryDTO
     */
    void updateCategory(CategoryDTO categoryDTO);


    /**
     * 根据类型查询分类
     * @param type
     */
    List<Category> selectByType(Integer type);
}
