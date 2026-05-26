package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sky.result.Result;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/category")
@Api(tags = "菜品与套餐分类管理功能")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
       log.info("新增分类：{}", categoryDTO);
       categoryService.add(categoryDTO);
       return Result.success();
    }


    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result<String> deleteCategory(@RequestBody Long id) {
       log.info("删除分类的id为：{}",id);
       categoryService.delete(id);
       return Result.success();
    }


    /**
     * 启用/禁用分类
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用/禁用分类")
    public Result<String> OpenOrNot(@PathVariable Integer status, @RequestParam Long id) {
        log.info("禁用id为：{},状态为{}",id,status);
        categoryService.OpenOrNot(id,status);
        return Result.success();
    }


    /**
     * 分页分类查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页分类查询")
    public Result<PageResult> selectByPages(CategoryPageQueryDTO categoryPageQueryDTO) {
       log.info("需要查询的信息："+categoryPageQueryDTO);
       PageResult pageResult = categoryService.selectByPages(categoryPageQueryDTO);
       return Result.success(pageResult);
    }


    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改分类")
    public Result<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("需要修改的信息:"+categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }


    /**
     * 根据类型查询分类
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> selectByType(@RequestParam Integer type) {
          log.info("需要查询的分类为：{}",type);
          categoryService.selectByType(type);

          return Result.success();
    }
}
