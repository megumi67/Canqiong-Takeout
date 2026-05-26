package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;


    /**
     * 新增分类
     * @param categoryDTO
     */
    @ApiOperation("新增分类")
    @Override
    public void add(CategoryDTO categoryDTO) {

        Category category = new Category();
        //复制数据
        BeanUtils.copyProperties(categoryDTO, category);

        //默认状态为禁用（0）
        category.setStatus(0);

        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);
    }





    /**
     * 删除分类
     * @param id
     */
    @ApiOperation("删除分类")
    @Override
    public void delete(Long id) {
        categoryMapper.delete(id);

    }


    /**
     * 启用/禁用分类
     * @param id
     * @param status
     */
    @ApiOperation("启用/禁用分类")
    @Override
    public void OpenOrNot(Long id, Integer status) {
        Category category = Category.builder().
                                     id(id).
                                     status(status).
                                     updateTime(LocalDateTime.now()).
                                     updateUser(BaseContext.getCurrentId()).
                                     build();
        categoryMapper.update(category);
    }


    /**
     * 分页分类查询
     * @param categoryPageQueryDTO
     * @return
     */
    @ApiOperation("分页分类查询")
    @Override
    public PageResult selectByPages(CategoryPageQueryDTO categoryPageQueryDTO) {
        //使用Pagehelper进行limit自动封装
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.selectByPages(categoryPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }


    /**
     * 修改分类
     * @param categoryDTO
     */
    @ApiOperation("修改分类")
    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        //复制信息
        BeanUtils.copyProperties(categoryDTO, category);

        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @ApiOperation("根据类型查询分类")
    @Override
    public List<Category> selectByType(Integer type) {
        categoryMapper.selectByType(type);
        return ;
    }


}
