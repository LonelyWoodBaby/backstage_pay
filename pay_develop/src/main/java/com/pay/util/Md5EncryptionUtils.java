package com.pay.util;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * @author liyabin
 *
 * 有很多需要进行加解密的地方，使用本工具类来提供加解密
 * 使用jar包为commons-codec包
 */
public class Md5EncryptionUtils {

    private static final int SALT_LENGTH = 6;
    /**
     * 基础的加密，将一段字符串MD5加密成密文
     * @param source
     * @return
     */
    public static String md5(String source){
        return DigestUtils.md5Hex(source);
    }

    /**
     * 使用盐将密码进行混淆加密
     * @param source
     * @param salt
     * @return
     */
    public static String md5WithSalt(String source,String salt){
        String passSource = saltConfuse(source,salt);
        return md5(passSource);
    }

    /**
     * 产生一个随机的十六进制数字生成的字符串作为盐
     * @return
     */
    public static String createRandomSalt(){
        StringBuffer result = new StringBuffer();
        for(int i=0;i< SALT_LENGTH;i++) {
            result.append(Integer.toHexString(new Random().nextInt(16)));
        }
        return result.toString().toUpperCase();
    }

    /**
     * 利用盐对字符串进行混淆
     * 本次的算法为：
     * 1.盐长度设置为最短6位
     * 2.将盐的前三位+原始密文+盐的后三位拼接为新的字符串
     * @param source
     * @param salt
     * @return
     */
    private static String saltConfuse(String source,String salt){
        int charNumber = salt.length()/2;
        return salt.substring(0,charNumber) + source + salt.substring(charNumber);
    }
}
