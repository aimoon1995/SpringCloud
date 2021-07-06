package com.moon.moon_api;

import com.moon.moon_api.service.AsyncService;
import com.moon.moon_commons.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SpringBootTest
@Slf4j
class MoonApiApplicationTests {
    @Autowired
    private  AsyncService asyncService;

    @Test
    void contextLoads() {
        Double addAmount = MathUtil.sub(MathUtil.div(2.05999999999999999993D,1),MathUtil.div(206,100));
        log.info("");
    }

    /**
     * 进行除法运算（四舍五入） scale:要保留的位数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double div(double d1, double d2, int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
