package com.moon.accept_num.task;

import com.moon.accept_num.mapper.NumItemMapper;
import com.moon.moon_commons.constants.CommonConstants;
import com.moon.moon_commons.entity.NumItemEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName DelTask
 * @Description: TODO
 * @Author zyl
 * @Date 2021/9/26
 * @Version V1.0
 **/
@Component
@EnableScheduling
public class DelTask {

    @Resource
    private NumItemMapper numItemMapper;

    @Scheduled(cron = "0 0 3 * * ?")
    public void delData () {
        // 查询全部未删除的数据进行删除
       List<NumItemEntity> numItemEntities = numItemMapper.getAllUnDelData();
       if (null != numItemEntities && numItemEntities.size() > 0) {
           for (NumItemEntity num:numItemEntities) {
               NumItemEntity numItemEntity = new NumItemEntity();
               numItemEntity.setUuid(num.getUuid());
               numItemEntity.setDelFlag(CommonConstants.DELED);
               numItemMapper.update(numItemEntity);
           }
       }
    }
}
