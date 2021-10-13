package com.moon.accept_num.mapper;

import com.moon.moon_commons.bean.NumDetailBean;
import com.moon.moon_commons.bean.TypeCountBean;
import com.moon.moon_commons.entity.NumItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName NumItemMapper
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@Mapper
public interface NumItemMapper {

    List<TypeCountBean> selectTypeCount(@Param("param") Map serMap);


    void insert(@Param("param") NumItemEntity numItemEntity);

    int selectUseAbleNumCount(@Param("mobile") String mobile);

    List<Integer> selectUseAbleNums(String openId);

    Integer selectMaxNum(@Param("date")String date);

    /**
     * @param
     * @return java.util.List<com.moon.moon_commons.bean.NumDetailBean>
     * @Author zyl
     * @Description 查询所有未作废的号
     * @Date 2021/7/28
     **/
    List<NumDetailBean> getNumList();

    NumItemEntity getByUuid(@Param("uuid") String uuid);

    int update(NumItemEntity numItemEntity);

    /**
     * @return java.util.List<com.moon.moon_commons.entity.NumItemEntity>
     * @Author zyl
     * @Description 查询后续未开始的号数相关信息
     * @Date 2021/8/9
     **/
    List<NumDetailBean> getWaitNums();

    /**
     * @param srhMap
     * @return com.moon.moon_commons.bean.NumDetailBean
     * @Author zyl
     * @Description 查询具体信息
     * @Date 2021/8/9
     **/
    NumDetailBean getNumInfo(@Param("param") Map srhMap);

    List<NumDetailBean> getFrontDoingNums(@Param("num") Integer num);

    List<NumItemEntity> getAllUnDelData();
}
