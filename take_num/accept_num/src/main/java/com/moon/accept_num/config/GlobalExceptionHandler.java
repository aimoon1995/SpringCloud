package com.moon.accept_num.config;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;


/**
 * 全局异常处理
 *
 * @author zyl
 * @date 2020-12-10
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 其他所有异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        log.info("handleException request url is {}", request.getRequestURI());
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (e instanceof ResultException) {
            log.error("ResultException  e.getStackTrace() :{}", e);
            out.print(createJsonObject(((ResultException) e).getErrorCode(), ((ResultException) e).getMsg(), null, false));
        } else {
            log.error("ResultException  e.getStackTrace() :{}", e);
            out.print(createJsonObject(10000, "server error", null, false));
        }
//        else if (e instanceof MissingServletRequestParameterException) {
//            log.error("MissingServletRequestParameterException  e.getStackTrace() :{}",Arrays.toString(e.getStackTrace()));
//            log.error("MissingServletRequestParameterException  e.fillInStackTrace() :{}",e.fillInStackTrace());
//        }

    }

    /**
     * 设置返回值
     *
     * @param returnCode
     * @param msg
     * @param data
     * @return
     */
    protected JSONObject createJsonObject(int returnCode, String msg, Object data, boolean success) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("returnCode", returnCode);
        jsonObject.put("msg", msg);
        jsonObject.put("data", data);
        jsonObject.put("success", success);
        jsonObject.put("returnTime", (new Date()).getTime());
        return jsonObject;
    }

}