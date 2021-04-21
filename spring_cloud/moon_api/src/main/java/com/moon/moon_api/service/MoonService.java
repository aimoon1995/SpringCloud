package com.moon.moon_api.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.moon.moon_api.mapper.moon.UserMapper;
import com.moon.moon_commons.entity.User;
import com.moon.moon_commons.util.ResponseBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName MoonService
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/21
 * @Version V1.0
 **/
@Service
public class MoonService {


    @Resource
    private UserMapper userMapper;

    public ResponseBean list(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userMapper.list();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        JSONObject data = new JSONObject();
        data.put("data",list);
        data.put("pageNum", pageInfo.getPageNum());
        data.put("total", pageInfo.getTotal());
        return ResponseBean.createSuccess("查询成功",data);
    }
}
