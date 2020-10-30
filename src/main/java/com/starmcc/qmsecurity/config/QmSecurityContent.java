package com.starmcc.qmsecurity.config;


import com.starmcc.qmsecurity.realm.QmSecurityRealm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qm
 * @date 2019年5月6日23:03:15
 * @Description 提供给调用者配置使用
 */
public class QmSecurityContent {

    private final static Map<String, Object> CACHE_MAP = new HashMap<>(16);

    static {
        CACHE_MAP.put("tokenSecret", "tokenSecret");
        CACHE_MAP.put("headerTokenKeyName", "token");
        CACHE_MAP.put("encryptNumber", 2);
    }

    /**
     * 设置token加密秘钥
     *
     * @param secret
     */
    public static void setTokenSecret(String secret) {
        CACHE_MAP.put("tokenSecret", secret);
    }

    /**
     * 获取token加密秘钥
     *
     * @return
     */
    public static String getTokenSecret() {
        return (String) CACHE_MAP.get("tokenSecret");
    }

    /**
     * 设置请求头和响应头中携带token的字段名
     *
     * @param secret
     */
    public static void setHeaderTokenKeyName(String key) {
        CACHE_MAP.put("headerTokenKeyName", key);
    }

    /**
     * 获取请求头中携带token的字段名
     *
     * @return
     */
    public static String getHeaderTokenKeyName() {
        return (String) CACHE_MAP.get("headerTokenKeyName");
    }

    /**
     * 设置token加密次数，底层调用AES对称加密算法
     *
     * @param secret
     */
    public static void setEncryptNumber(int num) {
        CACHE_MAP.put("encryptNumber", num);
    }

    /**
     * 获取token加密次数，底层调用AES对称加密算法
     *
     * @return
     */
    public static int getEncryptNumber() {
        return Integer.parseInt(CACHE_MAP.get("encryptNumber").toString());
    }

    /**
     * 设置realm
     *
     * @param realm
     */
    public static void setRealm(QmSecurityRealm realm) {
        CACHE_MAP.put("realm", realm);
    }

    /**
     * 设置realm
     *
     * @param realm
     */
    public static QmSecurityRealm getRealm() {
        return (QmSecurityRealm) CACHE_MAP.get("realm");
    }

}
