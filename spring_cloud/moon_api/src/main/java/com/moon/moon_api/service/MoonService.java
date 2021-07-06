package com.moon.moon_api.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.moon.moon_api.mapper.moon.UserMapper;
import com.moon.moon_commons.entity.User;
import com.moon.moon_commons.util.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName MoonService
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/21
 * @Version V1.0
 **/
@Service
@Slf4j
public class MoonService {

    @Autowired
    private AsyncService asyncService;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    public ResponseBean list(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.list();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        JSONObject data = new JSONObject();
        data.put("data", list);
        data.put("pageNum", pageInfo.getPageNum());
        data.put("total", pageInfo.getTotal());
        return ResponseBean.createSuccess("查询成功", data);
    }


    @Transactional(transactionManager = "moonTransactionManager", rollbackFor = RuntimeException.class)
    public void insertData(User user) {
        userMapper.insertData(user);
    }


    public void add() {
        try {
            for (int i = 2; i >= 0; i--) {
                User user = new User();
                user.setName("moon" + i);
                user.setUuid(UUID.randomUUID().toString());
                user.setBirth(new Date());
                // this.insertData(user);
                asyncService.B(user, i);
            }
        } catch (Exception e) {
            log.error("error");
        }

        //  asyncService.A();
    }
}
