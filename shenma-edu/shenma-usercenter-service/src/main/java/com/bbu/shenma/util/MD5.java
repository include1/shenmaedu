package com.bbu.shenma.util;

import java.security.MessageDigest;

//加密工具类
public class MD5 {
    /** 16进制的字符数组 */
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    /**
     * MD5加密操作
     * @param source 需要加密的原字符串
     * @param encoding  指定编码类型
     * @param uppercase  是否转为大写字符串
     * @return
     */
    public static String MD5Encode(String source, String encoding, boolean uppercase) {
        String result = null;
        try {
            result = source;
            // 获得MD5摘要对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用指定的字节数组更新摘要信息
            messageDigest.update(result.getBytes(encoding));
            // messageDigest.digest()获得16位长度
            result = byteArrayToHexString(messageDigest.digest());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return uppercase ? result.toUpperCase() : result;
    }

    /**
     * 转换字节数组为16进制字符串
     * @param bytes
     *  字节数组
     * @return
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte tem : bytes) {
            stringBuilder.append(byteToHexString(tem));
        }
        return stringBuilder.toString();
    }
    //加盐操作
    public static  String getNewPwd(String password){
        String salt = "~!@#$%^&*()_+;zmhgufdsf951";
        //获取密码
        password = salt + password;
        String newPassword = MD5.MD5Encode(password, "UTF-8", false);
        return newPassword;
    }
    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制对应的字符
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
