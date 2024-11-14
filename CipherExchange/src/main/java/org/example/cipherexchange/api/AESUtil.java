package org.example.cipherexchange.api;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {
    // 加密
    public static String encrypt(String data, String key, String vectorKey) throws Exception {
        // 创建AES密钥
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        // 获取 Cipher 实例，指定加密方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // 初始化向量
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());

        // 初始化 Cipher 为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        // 执行加密操作
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        // 将加密结果转换为 Base64 字符串并返回
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // 解密
    public static String decrypt(String encryptedData, String key, String vectorKey) throws Exception {
        // 创建 AES 密钥
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        // 获取 Cipher 实例
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // 初始化向量
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());

        // 初始化 Cipher 为解密模式
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        // 解密并转换为原始数据
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);

        // 返回解密后的字符串
        return new String(decryptedData);
    }
}
