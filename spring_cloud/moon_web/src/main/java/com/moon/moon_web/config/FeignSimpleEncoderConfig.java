
package com.moon.moon_web.config;


import com.alibaba.fastjson.JSON;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;

@Configuration
public class FeignSimpleEncoderConfig {
    private static Logger logger = LoggerFactory.getLogger(FeignSimpleEncoderConfig.class);

    @Bean
    public Encoder feignEncoder() {
        return new FormEncoder();
    }


    private static class FormEncoder implements Encoder {

        @Override
        public void encode(Object o, Type type, RequestTemplate rt) throws EncodeException {
            if(!(o instanceof Map))
                throw new EncodeException("Can only encode Map data");

            Map m = (Map)o;
            // XXX: quick n dirty!
            StringBuilder sb = new StringBuilder();

            for(Object k: m.keySet()) {
                if(!(k instanceof String))
                    throw new EncodeException("Can only encode String keys");

                if(sb.length() > 0)
                    sb.append("&");

                Object v = m.get(k);
                if(!(v instanceof String)) {
                    v = JSON.toJSONString(v);
                }
//                sb.append(k).append("=").append(v);
                try {

                    sb.append(URLEncoder.encode((String) k, "UTF-8"))
                            .append("=")
                            .append(URLEncoder.encode((String) v, "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    throw new EncodeException("Invalid encoding", ex);
                }
            }


            logger.info("callback data is {}", sb.toString());
            rt.header("Content-Type", "application/x-www-form-urlencoded");
            rt.body(sb.toString());
        }

    }
}
