package com.example.service;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:44
 * @version: 1.0
 */
public abstract class BaseServiceImpl <M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 降序排列
     * @param fields 排序属性
     * @return
     */
    public List<T> selectByDesc(String... fields) {
        return list(getSpecBySort(new QueryWrapper<T>(),fields,false));
    }
    /**
     * 升序排列
     * @param fields 排序属性
     * @return
     */
    public List<T> selectByAsc(String... fields) {
        return list(getSpecBySort(new QueryWrapper<T>(),fields,true));
    }


    /**
     *
     * @param obj 查询实体类
     * @param <E>
     * @return
     */
    public <E> Integer countByEntity(E obj) {
        return count(getSpecByEntity(obj));
    }

    /**
     *
     * @param obj 查询实体类
     * @param <E>
     * @return
     */
    public <E> List<T> selectByEntity(E obj) {
        return list(getSpecByEntity(obj));
    }

    /**
     *
     * @param obj 查询实体类
     * @param fields 排序属性
     * @param <E>
     * @return
     */
    public <E> List<T> selectByEntityAsc(E obj,String... fields) {
        QueryWrapper<T> qw = getSpecByEntity(obj);
        return list(getSpecBySort(qw,fields,true));
    }

    /**
     *
     * @param obj 查询实体类
     * @param fields 排序属性
     * @param <E>
     * @return
     */
    public <E> List<T> selectByEntityDesc(E obj,String... fields) {
        QueryWrapper<T> qw = getSpecByEntity(obj);
        return list(getSpecBySort(qw,fields,false));
    }

    /**
     *
     * @param obj 查询实体类
     * @param pageNum 页码
     * @param pageSize 页码大小
     * @param <E>
     * @return
     */
    public <E> Page<T> selectByEntity(E obj, Integer pageNum, Integer pageSize) {
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        return page(new Page<>(pageNum,pageSize),getSpecByEntity(obj));
    }

    /**
     *
     * @param obj 查询实体类
     * @param pageNum 页码
     * @param pageSize 页码大小
     * @param fields 排序属性
     * @param <E>
     * @return
     */
    public <E> Page<T> selectByEntityAsc(E obj,Integer pageNum,Integer pageSize,String... fields) {
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        QueryWrapper<T> qw = getSpecByEntity(obj);
        qw=getSpecBySort(qw,fields,true);
        return page(new Page<>(pageNum,pageSize),qw);
    }

    /**
     *
     * @param obj 查询实体类
     * @param pageNum 页码
     * @param pageSize 页码大小
     * @param fields 排序属性
     * @param <E>
     * @return
     */
    public <E> Page<T> selectByEntityDesc(E obj,Integer pageNum,Integer pageSize,String... fields) {
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        QueryWrapper<T> qw = getSpecByEntity(obj);
        qw=getSpecBySort(qw,fields,false);
        return page(new Page<>(pageNum,pageSize),qw);
    }

    /**
     * 分页查询（无查询条件）
     * @param pageNum 页码
     * @param pageSize 页码大小
     * @return
     */
    public Page<T> selectPage(Integer pageNum,Integer pageSize) {
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        return page(new Page<>(pageNum,pageSize),null);
    }

    /**
     * 分页降序查询（无查询条件）
     * @param pageNum 页码
     * @param pageSize 页码大小
     * @param fields 排序属性
     * @return
     */
    public Page<T> selectPageDesc(Integer pageNum,Integer pageSize,String... fields) {
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw=getSpecBySort(qw,fields,false);
        return page(new Page<>(pageNum,pageSize),qw);
    }

    /**
     * 分页升序查询（无查询条件）
     * @param pageNum 页码
     * @param pageSize 页码大小
     * @param fields 排序属性
     * @return
     */
    public Page<T> selectPageAsc(Integer pageNum,Integer pageSize,String... fields) {
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw=getSpecBySort(qw,fields,true);
        return page(new Page<>(pageNum,pageSize),qw);
    }


    /**
     * 获取拼接条件,只取查询实体类（E）的属性,并且值不为空，作为查询属性,Mybatis与jpa不同，要自己获取注解信息
     * 实现方法是查询实体类（E）与实体类T的字段比较，取实体类T字段的注解作为sql查询的列
     *
     * @param entity 查询实体类
     * @return
     */
    public <E> QueryWrapper<T> getSpecByEntity(E entity) {
        // 获取拼接条件
        QueryWrapper<T> qw = new QueryWrapper<>();
        if(entity==null){
            return qw;
        }
        //查询实体类E字段
        String[] entityFields = ReflectUtil.getFiledName(entity);

        //得到泛型T的字段和其上的注解（表中的列名）对应
        HashMap<String,String> map=new HashMap<>();

        //得到实体类的Class类
        ParameterizedType superclass = (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] types = superclass.getActualTypeArguments();
        //实体类T，对应第二个泛型
        Class clz=(Class)types[1];

        //获取字段反射类型
        Field[] declaredFields = clz.getDeclaredFields();
        //实体类字段和表列的对应关系
        for(Field field:declaredFields){
            String key=field.getName();
            String value=field.getName();
            //mybatis-plus特有列名对应字段注解
            TableField tableField = field.getAnnotation(TableField.class);
            TableId tableId = field.getAnnotation(TableId.class);
            if(tableField!=null){
                value=tableField.value();
            }
            if(tableId!=null){
                value=tableId.value();
            }
            map.put(key,value);
        }

        //查询实体类E只查找bean属性（有get，set方法），与实体类T的字段比对
        for (int i=0;i<entityFields.length;i++) {
            String field=entityFields[i];
            Object obj = ReflectUtil.getFieldValueByName(field, entity);
            if (obj != null) {
                if(map.containsKey(field)){
                    qw.eq(map.get(field),obj);
                }
            }
        }
        return qw;
    }

    /**
     * 获取拼接或条件,只取查询实体类（E）的属性,并且值不为空，作为查询属性,Mybatis与jpa不同，要自己获取注解信息
     * 实现方法是查询实体类（E）与实体类T的字段比较，取实体类T字段的注解作为sql查询的列
     *
     * @param entity 查询实体类
     * @return
     */
    public <E> QueryWrapper<T> getOrSpecByEntity(E entity,QueryWrapper<T> qw) {
        // 获取拼接条件
//        QueryWrapper<T> qw = new QueryWrapper<>();
        if(entity==null){
            return qw;
        }
        //查询实体类E字段
        String[] entityFields = ReflectUtil.getFiledName(entity);

        //得到泛型T的字段和其上的注解（表中的列名）对应
        HashMap<String,String> map=new HashMap<>();

        //得到实体类的Class类
        ParameterizedType superclass = (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] types = superclass.getActualTypeArguments();
        //实体类T，对应第二个泛型
        Class clz=(Class)types[1];

        //获取字段反射类型
        Field[] declaredFields = clz.getDeclaredFields();
        //实体类字段和表列的对应关系
        for(Field field:declaredFields){
            String key=field.getName();
            String value=field.getName();
            //mybatis-plus特有列名对应字段注解
            TableField tableField = field.getAnnotation(TableField.class);
            TableId tableId = field.getAnnotation(TableId.class);
            if(tableField!=null){
                value=tableField.value();
            }
            if(tableId!=null){
                value=tableId.value();
            }
            map.put(key,value);
        }
        //找最后一个or的字段索引
        int last=-1;
        for (int i=0;i<entityFields.length;i++) {
            String field=entityFields[i];
            Object obj = ReflectUtil.getFieldValueByName(field, entity);
            if (obj != null) {
                if(map.containsKey(field)){
                    last=i;
                }
            }
        }
        //查询实体类E只查找bean属性（有get，set方法），与实体类T的字段比对
        for (int i=0;i<entityFields.length;i++) {
            String field=entityFields[i];
            Object obj = ReflectUtil.getFieldValueByName(field, entity);
            if (obj != null) {
                if(map.containsKey(field)){
                    qw.eq(map.get(field),obj);
                    if(i!=last){
                        qw.or();
                    }
                }
            }
        }
        return qw;
    }


    /**
     * 获取排序的拼接条件,Mybatis与jpa不同，要自己获取注解信息,如不存在注解信息，字段名对应列名
     * 如果找不到实体类名，就从对应注解上的列名找
     *
     * @param qw 查询对象
     * @param fields 实体类字段
     * @param isAsc 是否升序，否则降序
     * @return
     */
    private QueryWrapper<T> getSpecBySort(QueryWrapper<T> qw,String[] fields,boolean isAsc) {
        HashMap<String,String> map=new HashMap<>();
        //列名，如果实体类字段找不到找不到，就从列名找
        HashSet<String> set=new HashSet<>();
        //得到实体类的Class类
        ParameterizedType superclass = (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] types = superclass.getActualTypeArguments();

        //实体类T，对应第二个泛型
        Class clz=(Class)types[1];

        Field[] declaredFields = clz.getDeclaredFields();
        for(Field field:declaredFields){
            String key=field.getName();
            String value=field.getName();
            TableField tableField = field.getAnnotation(TableField.class);
            TableId tableId = field.getAnnotation(TableId.class);
            if(tableField!=null){
                value=tableField.value();
                set.add(value);
            }
            if(tableId!=null){
                value=tableId.value();
                set.add(value);
            }
            map.put(key,value);
        }
        for(String field:fields){
            if(map.containsKey(field)){
                if(isAsc){
                    qw.orderByAsc(map.get(field));
                }else{
                    qw.orderByDesc(map.get(field));
                }
            }else if(set.contains(fields)){
                if(isAsc){
                    qw.orderByAsc(field);
                }else{
                    qw.orderByDesc(field);
                }
            }
        }
        return qw;
    }
}
