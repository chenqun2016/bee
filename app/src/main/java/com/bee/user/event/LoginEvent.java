package com.bee.user.event;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/14  21：11
 * 描述：
 */
public class LoginEvent {

    public String token;
    public LoginEvent(String token) {
        this.token = token;
    }
}
