package com.moon.accept_num.service;

import com.alibaba.fastjson.JSONObject;
import com.moon.accept_num.config.AppConfig;
import com.moon.accept_num.config.ResultException;
import com.moon.accept_num.mapper.CustTakeInfoMapper;
import com.moon.accept_num.mapper.CustomerMapper;
import com.moon.accept_num.mapper.NumItemMapper;
import com.moon.moon_commons.bean.NumDetailBean;
import com.moon.moon_commons.bean.TypeCountBean;
import com.moon.moon_commons.constants.CommonConstants;
import com.moon.moon_commons.entity.CustTakeInfoEntity;
import com.moon.moon_commons.entity.CustomerEntity;
import com.moon.moon_commons.entity.NumItemEntity;
import com.moon.moon_commons.util.DateUtil;
import com.moon.moon_commons.util.ResponseBean;
import com.moon.moon_commons.util.UUIDGenerator;
import com.moon.accept_num.utils.WxSendMsgUtil;
import com.moon.moon_commons.vo.TemplateDataVo;
import com.moon.moon_commons.vo.WxMssVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
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
            throw new ResultException("您还有未完成的号，请继续等待");
        }

        // 1、查询有没有此用户的信息，没有则新增,有则修改
        CustTakeInfoEntity custTakeInfoEntity = new CustTakeInfoEntity();
        CustomerEntity customer = new CustomerEntity();
        customer.setOpenId(openId);
        customer.setMobile(mobile);
        CustomerEntity customerEntity = customerMapper.selectInfoByParams(customer);
        if (null == customerEntity) {
            // 新增
            customer.setUuid(UUIDGenerator.get());
            customer.setCreateTime(new Date());
            customer.setMobile(mobile);
            customer.setOpenId(openId);
            customerMapper.insert(customer);
        } else {
            customer.setUuid(customerEntity.getUuid());
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
        String date = DateUtil.format(new Date(),DateUtil.DATE_FORMAT_DATE_M_1);
        Integer maxNum = numItemMapper.selectMaxNum(date);
        if (null == maxNum) {
            maxNum = 0;
        }
        log.info("maxNum->{}", maxNum);
        maxNum = maxNum + 1;
        //新增
        if (null != permCount && permCount > 0) {
            for (int i = 1; i <= permCount; i++) {
                NumItemEntity numItemEntity = new NumItemEntity();
                numItemEntity.setUuid(UUIDGenerator.get());
                numItemEntity.setType(CommonConstants.PERM_TYPE);
                numItemEntity.setNum(maxNum);
                numItemEntity.setCreateTime(new Date());
                numItemEntity.setUpdateTime(new Date());
                numItemEntity.setTakeUuid(custTakeInfoEntity.getUuid());
                numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_WAITING);
                numItemEntity.setDelFlag(CommonConstants.UNDEL);
                log.info("烫发取号->{}", maxNum);
                numItemMapper.insert(numItemEntity);
                maxNum = maxNum + 1;
            }
        }
        if (null != hairCutCount && hairCutCount > 0) {
            for (int i = 1; i <= hairCutCount; i++) {
                NumItemEntity numItemEntity = new NumItemEntity();
                numItemEntity.setUuid(UUIDGenerator.get());
                numItemEntity.setType(CommonConstants.HAIR_TYPE);
                numItemEntity.setNum(maxNum);
                numItemEntity.setCreateTime(new Date());
                numItemEntity.setUpdateTime(new Date());
                numItemEntity.setTakeUuid(custTakeInfoEntity.getUuid());
                numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_WAITING);
                numItemEntity.setDelFlag(CommonConstants.UNDEL);
                log.info("剪发取号->{}", maxNum);
                numItemMapper.insert(numItemEntity);
                maxNum = maxNum + 1;
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
    public ResponseBean start(String uuid, String openId) throws IOException {
        NumItemEntity itemEntity = numItemMapper.getByUuid(uuid);
        if (null == itemEntity) {
            throw new ResultException("查询不到此号");
        }
        if (!CommonConstants.TAKE_NUM_STATUS_WAITING.equals(itemEntity.getStatus())) {
            throw new ResultException("状态发生变化，请刷新页面");
        }
        NumItemEntity numItemEntity = new NumItemEntity();
        numItemEntity.setUuid(itemEntity.getUuid());
        numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_DOING);
        numItemEntity.setUpdateTime(new Date());
        numItemEntity.setLastUpdTime(itemEntity.getUpdateTime());
        if (numItemMapper.update(numItemEntity) < 1) {
            throw new ResultException("系统异常");
        }
        //拼接推送的模版
        WxSendMsgUtil wxSendMsgUtil = new WxSendMsgUtil();
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(openId);//用户的openid（要发送给那个用户，通常这里应该动态传进来的）
        wxMssVo.setTemplate_id(AppConfig.getTempleteId());//订阅消息模板id
        wxMssVo.setPage("pages/take/take");
        Map<String, TemplateDataVo> m = new HashMap<>(3);
//        当前叫号
//        {{thing1.DATA}}
//        您的排号
//        {{thing2.DATA}}
//        前方等候人数
//        {{thing3.DATA}}
//        备注
//        {{thing4.DATA}}
        Map srhMap = new HashMap();
        srhMap.put("uuid", uuid);
        NumDetailBean numDetailBean = numItemMapper.getNumInfo(srhMap);
//        m.put("thing1", new TemplateDataVo(itemEntity.getNum().toString() + "  号"));
//        m.put("thing2", new TemplateDataVo(itemEntity.getNum().toString() + "  号"));
//        m.put("thing3", new TemplateDataVo("0"));
//        m.put("thing4", new TemplateDataVo("已排到您的号数,请开始..."));
//        m.put("thing6", new TemplateDataVo("尾号"+numDetailBean.getMobile().substring(numDetailBean.getMobile().length()-4)+"——"+numDetailBean.getName()));
//        wxMssVo.setData(m);
//
//        log.info("点击开始的时候给当前号主推送->{}", JSONObject.toJSONString(wxMssVo));
//        wxSendMsgUtil.push(wxMssVo);
        // 给后面1个未开始的号用户推送即将开始的消息
        List<NumDetailBean> nums = numItemMapper.getWaitNums();
        if (nums.size() > 0) {
            for (NumDetailBean detailBean : nums) {
                //发送即将开始的信息
                m.clear();
                int waitNum = detailBean.getNum() - itemEntity.getNum();
                if (waitNum < 0) {
                    waitNum = 0;
                }
                wxMssVo.setTouser(detailBean.getOpenId());//用户的openid（要发送给那个用户，通常这里应该动态传进来的）
                m.put("thing1", new TemplateDataVo(itemEntity.getNum().toString() + "  号"));
                m.put("thing2", new TemplateDataVo(detailBean.getNum().toString() + "  号"));
                m.put("thing3", new TemplateDataVo(waitNum + ""));
                m.put("thing4", new TemplateDataVo("即将排到您的号数,请做好准备..."));
                m.put("thing6", new TemplateDataVo("尾号" + detailBean.getMobile().substring(detailBean.getMobile().length() - 4) + "——" + detailBean.getName()));
                wxMssVo.setData(m);
                log.info("点击开始的时候给下一个号主推送->{}", JSONObject.toJSONString(wxMssVo));
                wxSendMsgUtil.push(wxMssVo);
                break;
            }
        }
        return ResponseBean.createSuccess("操作成功");
    }

    /**
     * @param uuid
     * @return com.moon.moon_commons.util.ResponseBean
     * @Author zyl
     * @Description 完成
     * @Date 2021/7/28
     **/
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseBean complete(String uuid) throws IOException {
        Date sysDate = new Date();
        NumItemEntity itemEntity = numItemMapper.getByUuid(uuid);
        if (null == itemEntity) {
            throw new ResultException("查询不到此号");
        }
        if (!CommonConstants.TAKE_NUM_STATUS_DOING.equals(itemEntity.getStatus())) {
            throw new ResultException("状态发生变化，请刷新页面");
        }
        NumItemEntity numItemEntity = new NumItemEntity();
        numItemEntity.setUuid(itemEntity.getUuid());
        numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_COMPLETED);
        numItemEntity.setUpdateTime(sysDate);
        numItemEntity.setLastUpdTime(itemEntity.getUpdateTime());
        if (numItemMapper.update(numItemEntity) < 1) {
            throw new ResultException("系统异常");
        }
        //给下一个num的状态改为开始
        //给后面两个号推送微信消息
        List<NumDetailBean> beanList = numItemMapper.getWaitNums();
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTemplate_id(AppConfig.getTempleteId());//订阅消息模板id
        wxMssVo.setPage("pages/take/take");
        Map<String, TemplateDataVo> m = new HashMap<>(3);
        int currentNum = 0;
        if (beanList.size() > 0) {
            for (NumDetailBean numDetailBean : beanList) {
                currentNum = numDetailBean.getNum();
                wxMssVo.setTouser(numDetailBean.getOpenId());//用户的openid（要发送给那个用户，通常这里应该动态传进来的）
                String thing1 = currentNum + "  号";
                String thing2 = currentNum + "  号";
                String thing3 = (numDetailBean.getNum() - currentNum) + "号";
                String remark = "请做好准备...";
                //查询正在进行中的号
                List<NumDetailBean> doings = numItemMapper.getFrontDoingNums(currentNum);
                if (doings.size() > 0) {
                    thing1 = doings.get(0).getNum()+ "  号";
                    thing3 = (((currentNum - doings.get(0).getNum()) < 0)?0:(currentNum - doings.get(0).getNum()))+"号";
                }
                m.clear();
                m.put("thing1", new TemplateDataVo(thing1));
                m.put("thing2", new TemplateDataVo(thing2));
                m.put("thing3", new TemplateDataVo(thing3));
                m.put("thing4", new TemplateDataVo(remark));
                m.put("thing6", new TemplateDataVo("尾号" + numDetailBean.getMobile().substring(numDetailBean.getMobile().length() - 4) + "——" + numDetailBean.getName()));
                wxMssVo.setData(m);
                log.info("点击完成的时候给下一个号主推送->{}", JSONObject.toJSONString(wxMssVo));
                WxSendMsgUtil wxSendMsgUtil = new WxSendMsgUtil();
                wxSendMsgUtil.push(wxMssVo);
                break;
            }
        }
        return ResponseBean.createSuccess("操作成功");
    }


    /**
     * @param uuid
     * @return com.moon.moon_commons.util.ResponseBean
     * @Author zyl
     * @Description 作废
     * @Date 2021/7/28
     **/
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseBean invalidate(String uuid) throws IOException {
        Date sysDate = new Date();
        NumItemEntity itemEntity = numItemMapper.getByUuid(uuid);
        if (null == itemEntity) {
            throw new ResultException("查询不到此号");
        }
        if (CommonConstants.TAKE_NUM_STATUS_CANCELED.equals(itemEntity.getStatus())) {
            throw new ResultException("状态发生变化，请刷新页面");
        }
        NumItemEntity numItemEntity = new NumItemEntity();
        numItemEntity.setUuid(itemEntity.getUuid());
        numItemEntity.setStatus(CommonConstants.TAKE_NUM_STATUS_CANCELED);
        numItemEntity.setUpdateTime(new Date());
        numItemEntity.setLastUpdTime(itemEntity.getUpdateTime());
        if (numItemMapper.update(numItemEntity) < 1) {
            throw new ResultException("系统异常");
        }
        List<NumDetailBean> beanList = numItemMapper.getWaitNums();
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTemplate_id(AppConfig.getTempleteId());//订阅消息模板id
        wxMssVo.setPage("pages/take/take");
        Map<String, TemplateDataVo> m = new HashMap<>(3);
        if (beanList.size() > 0) {
            WxSendMsgUtil wxSendMsgUtil = new WxSendMsgUtil();
            int currentNum = 0;
            for (NumDetailBean numDetailBean : beanList) {
                wxMssVo.setTouser(numDetailBean.getOpenId());//用户的openid（要发送给那个用户，通常这里应该动态传进来的）
                String remark = "即将排到您的号数,请做好准备...";
                remark = "请做好准备...";
                currentNum = numDetailBean.getNum();
                wxMssVo.setTouser(numDetailBean.getOpenId());//用户的openid（要发送给那个用户，通常这里应该动态传进来的）
                String thing1 = currentNum + "  号";
                String thing2 = currentNum + "  号";
                String thing3 = (numDetailBean.getNum() - currentNum) + "号";
                //查询正在进行中的号
                List<NumDetailBean> doings = numItemMapper.getFrontDoingNums(currentNum);
                if (doings.size() > 0) {
                    thing1 = doings.get(0).getNum()+ "  号";
                    thing3 = (((currentNum - doings.get(0).getNum()) < 0)?0:(currentNum - doings.get(0).getNum()))+"号";
                }
                m.clear();
                m.put("thing1", new TemplateDataVo(thing1));
                m.put("thing2", new TemplateDataVo(thing2));
                m.put("thing3", new TemplateDataVo(thing3));
                m.put("thing4", new TemplateDataVo(remark));
                m.put("thing6", new TemplateDataVo("尾号" + numDetailBean.getMobile().substring(numDetailBean.getMobile().length() - 4) + "——" + numDetailBean.getName()));
                wxMssVo.setData(m);
                log.info("点击完成的时候给下一个号主推送->{}", JSONObject.toJSONString(wxMssVo));
                wxSendMsgUtil.push(wxMssVo);
                break;
            }
        }
        return ResponseBean.createSuccess("操作成功");
    }

    /**
     * 根据openid查询
     *
     * @param openId
     * @return
     */
    public CustomerEntity getUser(String openId) {
        CustomerEntity customer = new CustomerEntity();
        customer.setOpenId(openId);
        CustomerEntity customerEntity = customerMapper.selectInfoByParams(customer);
        return customerEntity;
    }


}
