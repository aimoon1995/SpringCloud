package com.moon.moon_api.mapper.moonSec;

import com.moon.moon_commons.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysConfigMapper {

    List<SysConfig> queryConfigByType(String type);

    List<SysConfig> queryConfigByKey(String key);
}