package com.moon.moon_api.service;

import com.moon.moon_api.mapper.moonSec.SysConfigMapper;
import com.moon.moon_commons.entity.SysConfig;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SysConfigService
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/21
 * @Version V1.0
 **/
@Service
public class SysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;

    public List<SysConfig> queryConfigByType(String type) {
       return sysConfigMapper.queryConfigByType(type);
    };

}
