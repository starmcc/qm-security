package com.starmcc.qmsecurity.utils;

import com.starmcc.qmsecurity.config.SecurityConstants;

import java.security.MessageDigest;

/**
 * @author qm
 * @date 2019/5/18 15:19
 * @Description 签名工具
 */
public class QmSignTools {

    /**
     * 签名
     *
     * @param str
     * @return
     */
    protected static String signMD5(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            MessageDigest mdTemp = MessageDigest.getInstance(SecurityConstants.SIGN_TYPE);
            mdTemp.update(str.getBytes(SecurityConstants.ENC_DEC_ENCODING));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = SecurityConstants.SIGN_DIGITS[byte0 >>> 4 & 0xf];
                buf[k++] = SecurityConstants.SIGN_DIGITS[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
