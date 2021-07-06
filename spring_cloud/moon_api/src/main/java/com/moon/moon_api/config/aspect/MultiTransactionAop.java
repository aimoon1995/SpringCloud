package com.moon.moon_api.config.aspect;

import com.moon.moon_api.config.annotation.MultiTransactional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MultiTransactionAop
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/22
 * @Version V1.0
 **/
@Aspect
@Component
public class MultiTransactionAop {

  //  private final ComboTransaction comboTransaction;

//    @Autowired
//    public MultiTransactionAop(ComboTransaction comboTransaction) {
//        this.comboTransaction = comboTransaction;
//    }
//
//    @Pointcut("@annotation(com.moon.moon_api.config.annotation.MultiTransactional)")
//    public void pointCut() {
//    }
//
//    @Around("pointCut() && @annotation(multiTransactional)")
//    public Object inMultiTransactions(ProceedingJoinPoint pjp, MultiTransactional multiTransactional) {
//        return comboTransaction.inCombinedTx(() -> {
//            try {
//                return pjp.proceed();
//            } catch (Throwable throwable) {
//                if (throwable instanceof RuntimeException) {
//                    throw (RuntimeException) throwable;
//                }
//                throw new RuntimeException(throwable);
//            }
//        }, multiTransactional.value());
//    }

}
