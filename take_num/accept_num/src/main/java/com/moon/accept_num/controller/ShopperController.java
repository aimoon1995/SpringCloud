package com.moon.accept_num.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.moon.accept_num.service.TakeNumService;
import com.moon.moon_commons.bean.NumDetailBean;
import com.moon.moon_commons.constants.CommonConstants;
import com.moon.moon_commons.util.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ShopperController
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/12
 * @Version V1.0
 **/
@RestController
@RequestMapping("/info/num/")
@Slf4j
public class ShopperController {

    @Autowired
    private TakeNumService takeNumService;
    /**
     * @return
     * @Author zyl
     * @Description 查询理发和烫发总人数
     * @Date 2021/7/9
     **/
    @RequestMapping("/type/count")
    @ResponseBody
    public ResponseBean getTypeCount() {
        Map serMap = new HashMap();
        Map typeCountBeans = takeNumService.getTypeCount(serMap);
        return ResponseBean.createSuccess("", typeCountBeans);
    }
    /**
     *查询列表 按照num和status排序
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseBean getList(@RequestParam(defaultValue = CommonConstants.defaultPageNum) Integer pageNum,
                                @RequestParam(defaultValue = CommonConstants.defaultPageSize) Integer pageSize) {
        if (pageNum < 0 || pageSize > 50 || pageSize < 0) {
            return ResponseBean.createError("非法参数");
        }
        PageHelper.startPage(pageNum, pageSize);
        //查询所有未作废的号
        List<NumDetailBean> beanList = takeNumService.getNumList();
        PageInfo<NumDetailBean> info = new PageInfo<NumDetailBean>(beanList);
        JSONObject data = new JSONObject();
        data.put("data",beanList);
        data.put("pageNum", pageNum);
        data.put("pages", info.getPages());
        data.put("isLast", (pageNum.equals(info.getPages())||(info.getPages()==0)));
        return ResponseBean.createSuccess("", data);
    }

    /**
     * 开始
     */
    @RequestMapping("/start")
    @ResponseBody
    public ResponseBean start(@RequestParam String uuid,
                              @RequestParam String openId) throws IOException {
        return  takeNumService.start(uuid,openId);
    }

    /**
     * 完成
     */
    @RequestMapping("/complete")
    @ResponseBody
    public ResponseBean complete(@RequestParam String uuid) throws IOException {
        return  takeNumService.complete(uuid);
    }
    /**
     * 作废
     */
    /**
     * 完成
     */
    @RequestMapping("/invalidate")
    @ResponseBody
    public ResponseBean invalidate(@RequestParam String uuid) throws IOException {
        return  takeNumService.invalidate(uuid);
    }

}
