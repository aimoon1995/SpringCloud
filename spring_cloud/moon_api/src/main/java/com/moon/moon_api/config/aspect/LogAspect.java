

package com.moon.moon_api.config.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(* com.moon.moon_api.controller.*.*(..))")
    public void webLog() {
    }


//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) {
//
//        try {
//            // 接收到请求，记录请求内容
//            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            HttpServletRequest request = attributes.getRequest();
//
//            StringBuilder reqParams = new StringBuilder();
//            Map<String, Object> params = getNameAndValue(joinPoint);
//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                if ("request".equals(entry.getKey()) || "base64".equals(entry.getKey())) {
//                    continue;
//                }
//                reqParams.append(entry.getKey());
//                reqParams.append(" = ");
//                reqParams.append(entry.getValue());
//                reqParams.append(" ");
//            }
//
//            StringBuilder sb = new StringBuilder();
//            sb.append("[URL]: ").append(request.getRequestURL().toString());
//            sb.append(" [HTTP_METHOD] : ").append(request.getMethod());
//            sb.append(" [IP] : ").append(request.getRemoteAddr());
//            sb.append(" [CLASS_METHOD] : ").append(joinPoint.getSignature().getDeclaringTypeName()).append(".").append(joinPoint.getSignature().getName());
//            sb.append(" [ARGS] : ").append(reqParams.toString());
//            log.info(sb.toString());
//        } catch (Exception e) {
//            log.error("***操作请求日志记录失败doBefore()***", e);
//        }
//    }


//    @AfterReturning(returning = "ret", pointcut = "webLog()")
//    public void AfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
//        if (ret instanceof ResponseBean) {
//            log.info("RESPONSE : " + JSON.toJSONString(ret));
//        } else {
//            log.info("RESPONSE : " + ret);
//        }
//    }


    /**
     * @Arround 必须有一个返回值，如果日志处理时候的异常抛出去，会直接返回给接口调用方
     */

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringBuilder sb = new StringBuilder();
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            StringBuilder reqParams = new StringBuilder();
            Map<String, Object> params = getNameAndValue(proceedingJoinPoint);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if ("request".equals(entry.getKey()) || "base64".equals(entry.getKey())) {
                    continue;
                }
                reqParams.append(entry.getKey());
                reqParams.append(" = ");
                reqParams.append(entry.getValue());
                reqParams.append(" ");
            }
            sb.append("[URL]: ").append(request.getRequestURL().toString());
            sb.append(" [HTTP_METHOD] : ").append(request.getMethod());
            sb.append(" [IP] : ").append(request.getRemoteAddr());
            sb.append(" [CLASS_METHOD] : ").append(proceedingJoinPoint.getSignature().getDeclaringTypeName()).append(".").append(proceedingJoinPoint.getSignature().getName());
            sb.append(" [ARGS] : ").append(reqParams.toString());
        } catch (Exception e) {
            log.error("接口入参日志记录失败{}", e.fillInStackTrace());
        } finally {
            Object result = proceedingJoinPoint.proceed();
            // 打印出参
            log.info(sb.toString());
            log.info("Response Args  : {}", JSONObject.toJSONString(result));
            return result;
        }
    }


    /**
     * 获取参数Map集合
     *
     * @param joinPoint
     * @return
     */
    Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();

        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

        for (int i = 0; i < paramNames.length; i++) {
            param.put(paramNames[i], paramValues[i]);
        }

        return param;
    }

}
