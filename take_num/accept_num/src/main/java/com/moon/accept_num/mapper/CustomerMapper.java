package com.moon.accept_num.mapper;

import com.moon.moon_commons.entity.CustomerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName CustomerMapper
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@Mapper
public interface CustomerMapper {

    CustomerEntity selectInfoByParams(@Param("param") CustomerEntity customer);

    void insert(@Param("param") CustomerEntity customer);

    void update(@Param("param") CustomerEntity customer);
}
