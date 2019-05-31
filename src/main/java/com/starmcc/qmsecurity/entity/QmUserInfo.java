package com.starmcc.qmsecurity.entity;

import java.util.Date;

/**
 * @author qm
 * @date 2018/12/22 19:58
 * @Description 框架用户对象
 */
public class QmUserInfo {
    /**
     * 用户身份唯一识别，框架以此作为主要依据进行整体校验基础。请保证必须唯一。
     */
    private String identify;
    /**
     * 用户对象，请勿把用户密码相关信息存放该属性。
     */
    private String user;
    /**
     * token签发时间,内部用于记录登录时间使用。
     */
    private Date signTime;
    /**
     * token多久会失效，单位(秒)。如果为0，则表示永不失效。默认为60 * 30
     */
    private long tokenExpireTime = 60L * 30;


    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public long getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }
}
