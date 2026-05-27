package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.aspect.AutoFillAspect;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 新增分类
     * @param category
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);



    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from category where id=#{id}")
    void deleteById(Long id);


    /**
     * 修改分类
     * @param category
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);


    /**
     * 分页分类查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> selectByPages(CategoryPageQueryDTO categoryPageQueryDTO);


    /**
     * 根据类型查询分类
     * @param type
     */
    List<Category> selectByType(Integer type);
}
