package com.moon.moon_api.mapper.moon;

import com.moon.moon_commons.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/21
 * @Version V1.0
 **/
@Mapper
public interface UserMapper {

    List<User> list();
}
