package com.starmcc.qmsecurity.exception;

/**
 * @author qm
 * @date 2019/5/7 9:12
 * @Description 签发token时系统错误
 */
public class QmSecurityCreateTokenException extends Exception {

    private static final String MSG = "签发token未知错误!";

    public QmSecurityCreateTokenException() {
        super(MSG);
    }

    public QmSecurityCreateTokenException(Throwable cause) {
        super(MSG, cause);
    }
}
