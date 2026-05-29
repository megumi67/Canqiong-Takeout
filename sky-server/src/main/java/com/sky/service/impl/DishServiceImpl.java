package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper flavorMapper;


    /**
     * 新增菜品
     * @param dishDTO
     */
    @Transactional//涉及多张表，开启事务管理
    @Override
    public void dishAddWithFlavors(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
       //向菜品表插入1条数据
        dishMapper.dishInsert(dish);

        //获取菜品id值（修改xml文件属性以获取id返回值）
        Long id = dish.getId();

        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        if(dishFlavors != null && dishFlavors.size()>0){
            //遍历并设置id
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
            //向口味表插入n条数据
            flavorMapper.insert(dishFlavors);
        }



    }
}
