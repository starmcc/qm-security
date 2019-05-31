package com.starmcc.qmsecurity.exception;

/**
 * @author qm
 * @date 2019/5/10 12:16
 * @Description 解密token失败！
 */
public class QmSecurityAnalysisTokenException extends Exception {

    private static final String MSG = "解密token失败！";

    public QmSecurityAnalysisTokenException() {
        super(MSG);
    }

    public QmSecurityAnalysisTokenException(Throwable cause) {
        super(MSG, cause);
    }
}
