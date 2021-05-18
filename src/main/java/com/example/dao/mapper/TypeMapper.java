package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.Type;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @ClassName:
 * @Description:
 * @author: baoguangyu
 * @date: 2021-04-21 13:42
 * @version: 1.0
 */
@Repository
public interface TypeMapper extends BaseMapper<Type> {

    // 添加商品类型
    @Insert("insert into type(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addType(Type type);

    // 更新商品类型
    @Update("update type set name = #{name} where  id = #{id}")
    public int update(Type type);
}
