package com.moon.accept_num.mapper;

import com.moon.moon_commons.entity.CustTakeInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName CustTakeInfoMapper
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@Mapper
public interface CustTakeInfoMapper {
    void insert(@Param("param")CustTakeInfoEntity custTakeInfoEntity);
}
