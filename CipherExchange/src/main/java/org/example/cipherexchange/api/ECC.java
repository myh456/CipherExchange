package org.example.cipherexchange.api;

import lombok.Data;

import java.util.Random;

@Data
public class ECC {
    // 取椭圆曲线 y² ≡ x³ + x + 1 (mod 13)
    // 私钥
    private int d;
    // 公钥
    private ECPoint P;
    // 基点
    private final ECPoint G = new ECPoint((byte) 5, (byte) 1);
    public ECPoint SubKey() {
        this.d = (new Random()).nextInt(1, 13);
        return this.G.mul(d);
    }
}
