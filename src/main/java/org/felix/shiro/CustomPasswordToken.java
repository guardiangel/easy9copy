package org.felix.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author Felix
 */
public class CustomPasswordToken extends UsernamePasswordToken {

    private String token;

    public CustomPasswordToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
