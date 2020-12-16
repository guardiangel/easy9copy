package org.felix.utils;

import org.springframework.stereotype.Component;

@Component
public class StaticInitializerUtil {
    public StaticInitializerUtil(TokenSettings tokenSettings) {
        JwtTokenUtil.setTokenSettings(tokenSettings);
    }
}
