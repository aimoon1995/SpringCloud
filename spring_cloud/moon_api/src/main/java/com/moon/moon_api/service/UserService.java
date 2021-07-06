package com.moon.moon_api.service;

import com.moon.moon_api.config.ResultException;
import com.moon.moon_api.mapper.moon.UserMapper;
import com.moon.moon_commons.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName UserService
 * @Description: TODO
 * @Author zyl
 * @Date 2021/6/2
 * @Version V1.0
 **/
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    MoonService moonService;

    @Transactional(transactionManager = "moonTransactionManager", rollbackFor = RuntimeException.class)
    public void insert(User user,int i) throws ResultException {
            userMapper.insertData(user);
            try {
                int a = 3 / i;
            } catch (Exception e) {
                throw  new ResultException("ERROR",1);
            }
    }
}
