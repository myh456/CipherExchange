package org.example.cipherexchange.controller;

import org.example.cipherexchange.api.AESUtil;
import org.example.cipherexchange.api.ECC;
import org.example.cipherexchange.api.ECPoint;
import org.example.cipherexchange.api.Result;
import org.example.cipherexchange.service.ECDHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/ecdh")
public class ECDHController {
    @Autowired
    ECDHService ecdhService;
    ECC pri = new ECC();
    private String SharedKey;

    @GetMapping("/requestPub")
    public Result<ECPoint> RequestPub(int x, int y) {
        pri.setP(pri.SubKey());
        this.SharedKey = ecdhService.GenerateKey(pri.getD(), new ECPoint((byte) x, (byte) y));
        System.out.println("密钥交换结果===>" + this.SharedKey);
        return Result.success(pri.getP());
    }

    @GetMapping("/requestMsg")
    public Result<String> RequsetMsg() throws Exception {
        // 生成一个随机IV
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder iv = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(CHARACTERS.length());
            iv.append(CHARACTERS.charAt(index));
        }
        String plain = "Hello ECDH";
        return Result.success(iv.toString() + AESUtil.encrypt(plain, SharedKey, iv.toString()));
    }
}