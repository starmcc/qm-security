package com.starmcc.qmsecurity.exception;

/**
 * @author qm
 * @date 2019/5/6 21:50
 * @Description 登录异常
 */
public class QmSecurityQmUserInfoException extends Exception {

    private static final String MSG = "请检查qmUserInfo的参数是否完整！";

    public QmSecurityQmUserInfoException() {
        super(MSG);
    }

    public QmSecurityQmUserInfoException(Throwable cause) {
        super(MSG, cause);
    }
}
