package org.example.cipherexchange.service.impl;

import org.example.cipherexchange.api.ECPoint;
import org.example.cipherexchange.service.ECDHService;
import org.springframework.stereotype.Service;

@Service
public class ECDHServiceImpl implements ECDHService {
    @Override
    public String GenerateKey(int APri, ECPoint BPub) {
        String SharedKey = "";
        ECPoint S = BPub.mul(APri);
        // 对结果坐标进行拼接
        return String.format("%08d", S.x) + String.format("%08d", S.y);
    }
}
