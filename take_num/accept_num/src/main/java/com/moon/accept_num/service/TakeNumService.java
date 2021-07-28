package com.moon.accept_num.service;

import com.moon.accept_num.mapper.CustTakeInfoMapper;
import com.moon.accept_num.mapper.CustomerMapper;
import com.moon.accept_num.mapper.NumItemMapper;
import com.moon.moon_commons.bean.NumDetailBean;
import com.moon.moon_commons.bean.TypeCountBean;
import com.moon.moon_commons.constants.CommonConstants;
import com.moon.moon_commons.entity.CustTakeInfoEntity;
import com.moon.moon_commons.entity.CustomerEntity;
import com.moon.moon_commons.entity.NumItemEntity;
import com.moon.moon_commons.util.ResponseBean;
import com.moon.moon_commons.util.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName TakeNumService
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@Service
@Slf4j
public class TakeNumService {
    @Resource
    private NumItemMapper numItemMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private CustTakeInfoMapper custTakeInfoMapper;

    public Map getTypeCount(Map serMap) {
        List<TypeCountBean> typeCountBeans = numItemMapper.selectTypeCount(serMap);
        Map<String, Integer> dataMap = new HashMap<String, Integer>();
        dataMap.put("permCount", 0);
        dataMap.put("hairCutCount", 0);
        if (null != typeCountBeans && typeCountBeans.size() > 0) {
            for (TypeCountBean typeCountBean : typeCountBeans) {
                if (CommonConstants.PERM_TYPE.equals(typeCountBean.getType())) {
                    dataMap.put("permCount", typeCountBean.getNumCount());
                } else {
                    dataMap.put("hairCutCount", typeCountBean.getNumCount());
                }
            }
        }
        log.info("type/count", dataMap);
        return dataMap;
    }


    /**
     * 取号  需要集成redis锁
     *
     * @param permCount
     * @param hairCutCount
     * @param name
     * @param mobile
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseBean takeNum(Integer permCount, Integer hairCutCount, String name, String mobile, String openId) {
        log.info("取号参数openId{}", openId);
        //一个号码只能取一次
        int numCount = numItemMapper.selectUseAbleNumCount(mobile);
        if (numCount > 0) {
            return ResponseBean.createError("您还有未完成的号，请继续等待");
        }

        // 1、查询有没有此用户的信息，没有则新增,有则修改
        CustTakeInfoEntity custTakeInfoEntity = new CustTakeInfoEntity();
        CustomerEntity customer = new CustomerEntity();
        customer.setOpenId(openId);
        CustomerEntity customerEntity = customerMapper.selectInfoByParams(customer);
        if (null == customerEntity) {
            // 新增
            customer.setUuid(UUIDGenerator.get());
            customer.setMobile(mobile);
            customer.setOpenId(openId);
            customerMapper.insert(customer);
        } else {
            customer.setUuid(customerEntity.getUuid());
            customer.setOpenId(openId);
            customerMapper.updateDynamic(customer);
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
        Integer maxNum = numItemMapper.selectMaxNum();
        if (null == maxNum) {
            maxNum = 0;
        }
        maxNum = maxNum + 1;
        //新增
        if (null != permCount && permCount > 0) {
            for (int i = 1; i <= permCount; i++) {
                NumItemEntity numItemEntity = new NumItemEntity();
                numItemEntity.setUuid(UUIDGenerator.get());
                numItemEntity.setType(CommonConstants.PERM_TYPE);
                numItemEntity.setNum(maxNum);
                numItemEntity.setCreateTime(new Date());
                numItemEntity.setTakeUuid(custTakeInfoEntity.getUuid());
                numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_WAITING);
                numItemMapper.insert(numItemEntity);
                maxNum = maxNum + i;
            }
        }
        if (null != hairCutCount && hairCutCount > 0) {
            for (int i = 1; i <= hairCutCount; i++) {
                NumItemEntity numItemEntity = new NumItemEntity();
                numItemEntity.setUuid(UUIDGenerator.get());
                numItemEntity.setType(CommonConstants.HAIR_TYPE);
                numItemEntity.setNum(maxNum);
                numItemEntity.setCreateTime(new Date());
                numItemEntity.setTakeUuid(custTakeInfoEntity.getUuid());
                numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_WAITING);
                numItemMapper.insert(numItemEntity);
                maxNum = maxNum + i;
            }
        }
        return ResponseBean.createSuccess("取号成功");
    }


    /**
     * @param openId
     * @return com.moon.moon_commons.util.ResponseBean
     * @Author zyl
     * @Description 获取当前有效的号码
     * @Date 2021/7/20
     **/
    public ResponseBean getCustUserAbleNum(String openId) {
        List<Integer> nums = numItemMapper.selectUseAbleNums(openId);
        log.info("当前opeind-{},有效的号数{}", openId, nums);
        String numStr = "";
        if (nums.size() > 0) {
            numStr = "您当前在排号数为 ";
            for (Integer num : nums) {
                numStr = numStr + num + "号 ";
            }
            numStr = numStr + ",请您耐心等待...";
        }

        return ResponseBean.createSuccess(numStr);
    }

    /**
     * @param
     * @return java.util.List<com.moon.moon_commons.bean.NumDetailBean>
     * @Author zyl
     * @Description 查询所有未作废的号
     * @Date 2021/7/28
     **/
    public List<NumDetailBean> getNumList() {
        List<NumDetailBean> beanList = numItemMapper.getNumList();
        return beanList;
    }

    /**
     * @param uuid
     * @return com.moon.moon_commons.util.ResponseBean
     * @Author zyl
     * @Description 开始
     * @Date 2021/7/28
     **/
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseBean start(String uuid) {
        NumItemEntity itemEntity = numItemMapper.getByUuid(uuid);
        if (null == itemEntity) {
            return ResponseBean.createError("查询不到此号");
        }
        if (!CommonConstants.TAKE_NUM_STATUS_WAITING.equals(itemEntity.getStatus())) {
            return ResponseBean.createError("状态发生变化，请刷新页面");
        }
        NumItemEntity  numItemEntity = new NumItemEntity();
        numItemEntity.setUuid(itemEntity.getUuid());
        numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_DOING);
        numItemEntity.setUpdateTime(new Date());
        numItemEntity.setLastUpdTime(itemEntity.getUpdateTime());
        if (numItemMapper.update(numItemEntity) < 1) {
            return ResponseBean.createError("系统异常");
        }
        return  ResponseBean.createSuccess("");
    }

    /**
     * @param uuid
     * @return com.moon.moon_commons.util.ResponseBean
     * @Author zyl
     * @Description 完成
     * @Date 2021/7/28
     **/
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseBean complete(String uuid) {
        NumItemEntity itemEntity = numItemMapper.getByUuid(uuid);
        if (null == itemEntity) {
            return ResponseBean.createError("查询不到此号");
        }
        if (!CommonConstants.TAKE_NUM_STATUS_DOING.equals(itemEntity.getStatus())) {
            return ResponseBean.createError("状态发生变化，请刷新页面");
        }
        NumItemEntity  numItemEntity = new NumItemEntity();
        numItemEntity.setUuid(itemEntity.getUuid());
        numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_COMPLETED);
        numItemEntity.setUpdateTime(new Date());
        numItemEntity.setLastUpdTime(itemEntity.getUpdateTime());
        if (numItemMapper.update(numItemEntity) < 1) {
            return ResponseBean.createError("系统异常");
        }
        //给下一个num的状态改为开始
        //给后面两个号推送微信消息
        // 查询后面两个状态为未开始的号以及对应的openId
        List<NumDetailBean> beanList = numItemMapper.getNumList();
        beanList =  beanList.stream().filter(a -> CommonConstants.TAKE_NUM_STATUS_WAITING.equals(a.getStatus()) && a.getNum() > itemEntity.getNum()).collect(Collectors.toList());
        if (beanList.size() > 0) {
            NumDetailBean numDetailBean = null;
            for (int i = 0; i < beanList.size(); i++) {
                numDetailBean = beanList.get(i);
                if (i == 0) {
                     //状态改为进行中，并推送开始理发的消息

                 } else if (i>0 && i<=2) {
                     //推送即将开始的消息

                 } else {
                     break;
                 }
            }
        }
        return  ResponseBean.createSuccess("");
    }
}
