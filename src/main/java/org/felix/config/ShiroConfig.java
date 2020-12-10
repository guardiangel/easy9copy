package org.felix.config;

import org.felix.shiro.RedisCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher() {
        return new CustomHashedCredentialsMatcher();
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    @Bean
    public CustomRealm customRealm() {

        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
        customRealm.setCacheManager(redisCacheManager());

        return customRealm;
    }


}
