package org.felix.utils;

import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.felix.service.RedisService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix
 */
@Component
public class RedisCheckUtil {

    @Resource
    private RedisService redisService;

    public void operFrequencyCheck(String redisKey, String redisValue,
                                   long time, TimeUnit timeUnit, String errMsg) {

        Object redisContent = redisService.get(redisKey);

        if (redisContent == null) {
            redisService.set(redisKey, redisValue, time, timeUnit);
        } else {
            if (Tool.isBlank(errMsg)) {
                throw new ServiceException(BaseResponseCode.OPER_SO_FAST);
            } else {
                throw new ServiceException(errMsg);
            }
        }
    }
}
