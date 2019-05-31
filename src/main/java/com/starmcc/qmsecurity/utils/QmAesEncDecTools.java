package com.starmcc.qmsecurity.utils;


import com.starmcc.qmsecurity.config.SecurityConstants;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author qm
 * @date 2019/5/18 15:06
 * @Description Aes底层数据安全工具
 */
public class QmAesEncDecTools {

    /**
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(SecurityConstants.ENC_DEC_TYPE);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes(SecurityConstants.ENC_DEC_ENCODING));
        kgen.init(128, secureRandom);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, SecurityConstants.ENC_DEC_TYPE);
        Cipher cipher = Cipher.getInstance(SecurityConstants.ENC_DEC_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes(SecurityConstants.ENC_DEC_ENCODING));
        return Base64.encodeBase64String(encryptedData);
    }


    /**
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(SecurityConstants.ENC_DEC_TYPE);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes(SecurityConstants.ENC_DEC_ENCODING));
        kgen.init(128, secureRandom);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, SecurityConstants.ENC_DEC_TYPE);
        Cipher cipher = Cipher.getInstance(SecurityConstants.ENC_DEC_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decryptedData = cipher.doFinal(Base64.decodeBase64(data));
        return new String(decryptedData, SecurityConstants.ENC_DEC_ENCODING);
    }
}
