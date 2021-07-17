package com.moon.accept_num.service;

import com.moon.accept_num.mapper.CustTakeInfoMapper;
import com.moon.accept_num.mapper.CustomerMapper;
import com.moon.accept_num.mapper.NumItemMapper;
import com.moon.moon_commons.bean.TypeCountBean;
import com.moon.moon_commons.constants.CommonConstants;
import com.moon.moon_commons.entity.CustTakeInfoEntity;
import com.moon.moon_commons.entity.CustomerEntity;
import com.moon.moon_commons.entity.NumItemEntity;
import com.moon.moon_commons.util.ResponseBean;
import com.moon.moon_commons.util.UUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TakeNumService
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@Service
public class TakeNumService {
    @Resource
    private NumItemMapper numItemMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private CustTakeInfoMapper custTakeInfoMapper;

    public Map getTypeCount() {
        List<TypeCountBean> typeCountBeans  =  numItemMapper.selectTypeCount();
        Map<String,Integer> dataMap = new HashMap<String,Integer>();
        dataMap.put("permCount",0);
        dataMap.put("hairCutCount",0);
        if (null != typeCountBeans && typeCountBeans.size() > 0) {
            for (TypeCountBean typeCountBean:typeCountBeans) {
                if (CommonConstants.PERM_TYPE.equals(typeCountBean.getType())) {
                    dataMap.put("permCount",typeCountBean.getNumCount());
                } else {
                    dataMap.put("hairCutCount",typeCountBean.getNumCount());
                }
            }
        }
        return  dataMap;
    }


    /**
     * 取号  需要集成redis锁
     * @param permCount
     * @param hairCutCount
     * @param name
     * @param mobile
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseBean takeNum(Integer permCount, Integer hairCutCount, String name, String mobile,String openId) {
         // 1、查询有没有此用户的信息，没有则新增,有则修改
        CustTakeInfoEntity custTakeInfoEntity = new CustTakeInfoEntity();
        CustomerEntity customer = new CustomerEntity();
        customer.setMobile(mobile);
        CustomerEntity customerEntity = customerMapper.selectInfoByParams(customer);
        if (null == customerEntity) {
            // 新增
            customer.setUuid(UUIDGenerator.get());
            customer.setOpenId(openId);
            customerMapper.insert(customer);
        } else {
            customer.setUuid(customerEntity.getUuid());
            customer.setOpenId(openId);
            customerMapper.update(customer);
        }
        //2、新增本次取号数据
        custTakeInfoEntity.setUuid(UUIDGenerator.get());
        custTakeInfoEntity.setCustUuid(customer.getUuid());
        custTakeInfoEntity.setCreateTime(new Date());
        custTakeInfoEntity.setName(name);
        custTakeInfoEntity.setPermCount(permCount);
        custTakeInfoEntity.setHairCutCount(hairCutCount);
        custTakeInfoMapper.insert(custTakeInfoEntity);
        //3 查询不同类型目前最大的号
        List<TypeCountBean>  typeCountBeans =   numItemMapper.selectMaxNumByType();
        Integer perMaxNum = 0;
        Integer hairCutMaxNum = 0;
        for (TypeCountBean countBean:typeCountBeans) {
            if (CommonConstants.PERM_TYPE.equals(countBean.getType())) {
                perMaxNum = countBean.getNumCount();
            } else {
                hairCutMaxNum = countBean.getNumCount();
            }
        }
        //新增
        if (null != permCount && permCount > 0) {
            for (int i = 1; i <=permCount; i++) {
                NumItemEntity numItemEntity = new NumItemEntity();
                numItemEntity.setUuid(UUIDGenerator.get());
                numItemEntity.setType(CommonConstants.PERM_TYPE);
                numItemEntity.setNum(perMaxNum + i);
                numItemEntity.setCreateTime(new Date());
                numItemEntity.setTakeUuid(custTakeInfoEntity.getUuid());
                numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_WAITING);
                numItemMapper.insert(numItemEntity);
            }
        }
        if (null != hairCutCount && hairCutCount > 0) {
            for (int i = 1; i <=hairCutCount; i++) {
                NumItemEntity numItemEntity = new NumItemEntity();
                numItemEntity.setUuid(UUIDGenerator.get());
                numItemEntity.setType(CommonConstants.HAIR_TYPE);
                numItemEntity.setNum(hairCutMaxNum + i);
                numItemEntity.setCreateTime(new Date());
                numItemEntity.setTakeUuid(custTakeInfoEntity.getUuid());
                numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_WAITING);
                numItemMapper.insert(numItemEntity);
            }
        }
        return ResponseBean.createSuccess("取号成功");
    }
}
