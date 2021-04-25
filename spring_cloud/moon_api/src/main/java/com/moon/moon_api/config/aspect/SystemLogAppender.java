package com.moon.moon_api.config.aspect;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import com.moon.moon_api.config.ResultException;
import com.moon.moon_commons.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

@Slf4j
public class SystemLogAppender extends AppenderBase<LoggingEvent> {

//	@Override
//	public void close() {
//		// TODO Auto-generated method stub
//		
//	}

    //	@Override
//	public boolean requiresLayout() {
//		// TODO Auto-generated method stub
//		return false;
//	}
    @Override
    protected void append(LoggingEvent event) {
        // 过滤其它日志
        if (event.getLoggerName().equals(LogAspect.class.getName())) {
            return;
        }
        // 过滤业务异常
        if (event.getThrowableProxy() != null && ((ThrowableProxy) event.getThrowableProxy()).getThrowable() instanceof ResultException) {
            return;
        }
        log.info("logName:{}", event.getLoggerName());
        log.info("logLevel:{}", StringUtil.getOrElse(event.getLevel()));
        String defaultMsg = "Undefined Error";
        defaultMsg = StringUtils.isEmpty(event.getMessage()) ? defaultMsg : event.getMessage();
        log.info("logMsg:{}", StringUtil.getOrElse(event.getMessage(), defaultMsg));
        log.info("logStackTrace:{}", StringUtil.stackTraceToString(event.getThrowableProxy() != null ? event.getThrowableProxy().getStackTraceElementProxyArray() : null));
    }


}
