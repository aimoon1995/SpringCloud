package com.moon.accept_num.controller;

import com.moon.accept_num.service.TakeNumService;
import com.moon.moon_commons.bean.TypeCountBean;
import com.moon.moon_commons.util.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TakeNumController
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@RestController
@RequestMapping("/take/num/")
public class CustomerController {

     @Autowired
     private TakeNumService takeNumService;
    /**
      * @Author zyl
      * @Description 查看用户前面还有多少人排队
      * @Date 2021/7/9
      * @return
      **/
     @RequestMapping("/type/count")
     public ResponseBean  getTypeCount () {
          Map typeCountBeans =  takeNumService.getTypeCount();
          return  ResponseBean.createSuccess("",typeCountBeans);
     }


     /**
      * 取号
      * @param permCount
      * @param hairCutCount
      * @param name
      * @param mobile
      * @return
      */
     @RequestMapping("/take")
     public ResponseBean  takeNum (
             @RequestParam Integer permCount,
             @RequestParam Integer hairCutCount,
             @RequestParam String name,
             @RequestParam String mobile
             ) {
          if (permCount == 0 && hairCutCount == 0) {
               ResponseBean.createError("必须取一个号");
          }
          return takeNumService.takeNum(permCount,hairCutCount,name,mobile);
     }
}
