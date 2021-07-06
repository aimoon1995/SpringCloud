package com.moon.moon_api.service;

import com.moon.moon_api.config.ResultException;
import com.moon.moon_api.mapper.moon.UserMapper;
import com.moon.moon_commons.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName AsyncService
 * @Description: TODO
 * @Author zyl
 * @Date 2021/6/1
 * @Version V1.0
 **/
@Slf4j
@Service
public class AsyncService {
    @Resource
    private UserService userService;

    @Async
    public void  A () throws ResultException {
        for (int i=3;i>=-1;i--) {
            User user = new User();
            user.setName("查月亮"+i);
            user.setUuid(UUID.randomUUID().toString());
            user.setBirth( new Date());
           // userService.addData(user,i);
           // moonService.insertData(user);
            //C(user,i);
        }
    }

    @Async
    public void  B (User user,int i) throws Exception {
            userService.insert(user,i);
            if (i == 1) {
                user.setName("1221312312");
                userService.insert(user,1);
            }
        // moonService.insertData(user);
            //C(user,i);
    }
//    @Async("taskExecutor")
//    @Transactional(transactionManager = "moonTransactionManager",rollbackFor = RuntimeException.class)
//    public void  C(User user,int i){
//        userService.insertData(user);
//        int a= 3/i;
//    }
}
