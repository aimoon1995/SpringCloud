package com.moon.accept_num.mapper;

import com.moon.moon_commons.bean.TypeCountBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName NumItemMapper
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@Mapper
public interface NumItemMapper {

    List<TypeCountBean> selectTypeCount();
}
