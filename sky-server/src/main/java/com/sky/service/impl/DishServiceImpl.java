package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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

    @Autowired
    SetMealDishMapper setMealDishMapper;


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


    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> pages = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(pages.getTotal(),pages.getResult());
    }



    /**
     * 批量删除菜品
     * @param ids
     */
    @Override
    @Transactional
    public void deleteDish(List<Long> ids) {
        //先判断当前菜品的状态（是否起售）,只要有一个id处于起售状态时，直接退出，无需执行后面的逻辑
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                //当前菜品处于起售中，不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }


        //判断当前菜品是否被套餐关联（从中间表查询）
        List<Long> setMealIds = setMealDishMapper.selectByDishIds(ids);
        if(setMealIds != null && setMealIds.size()>0){
            //有菜品被套餐关联，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

//        //使用循环删除菜品表中的数据
//        for (Long id : ids) {
//            dishMapper.deleteById(id);
//            //删除与菜品关联的口味数据
//            flavorMapper.deleteByDishId(id);
//        }

        //根据id集合批量删除菜品
        dishMapper.deleteByIds(ids);
        //根据菜品id批量删除口味
        flavorMapper.deleteByDishIds(ids);
    }


    /**
     *根据id查询菜品
     * @param id
     * @return
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        //根据id查询菜品数据
        Dish dish = dishMapper.getById(id);
        //根据id查询菜品id对应的口味数据
        List<DishFlavor> dishFlavorList = flavorMapper.getById(id);
        //结果封装
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    /**
     * 根据id修改菜品
     * @param dishDTO
     */
    @Override
    public void updateDish(DishDTO dishDTO) {
        //修改菜品表
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        //对于口味表，先删除
        flavorMapper.deleteByDishId(dishDTO.getId());

        //再重新插入
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        if(dishFlavors != null && dishFlavors.size()>0){
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dish.getId());
            });
            flavorMapper.insert(dishFlavors);
        }
    }
}
