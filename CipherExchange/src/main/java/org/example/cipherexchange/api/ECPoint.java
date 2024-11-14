package org.example.cipherexchange.api;

public class ECPoint {
    // 点坐标(-1时为无穷远点)
    public byte x = -1;
    public byte y = -1;
    // 椭圆曲线参数
    private byte a = 1, b = 1, p = 13;

    public ECPoint() {}

    public ECPoint(byte x, byte y) {
        this.x = x;
        this.y = y;
    }

    public ECPoint(byte x, byte y, byte a, byte b, byte p) {
        this.x = x;
        this.y = y;
        this.a = a;
        this.b = b;
        this.p = p;
    }

    // 域内取模运算
    private static byte mod(byte a, byte m) {
        byte result = (byte) (a % m);
        if (result < 0) {
            result += m;
        }
        return result;
    }

    // 计算a在m下的逆元
    private static byte modInverse(byte a, byte m) {
        byte m0 = m, t, q;
        byte x0 = 0, x1 = 1;

        if (m == 1) return 0;

        while (a > 1) {
            q = (byte) (a / m);
            t = m;
            m = (byte) (a % m);
            a = t;
            t = x0;
            x0 = (byte) (x1 - q * x0);
            x1 = t;
        }

        if (x1 < 0) x1 += m0;
        return x1;
    }

    // 判断是否是无穷远点
    public boolean isInfinity() {
        return x == -1 && y == -1;
    }

    // 计算点加
    public ECPoint add(ECPoint Q) {
        if (this.isInfinity()) return Q;
        if (Q.isInfinity()) return this;
        if (this.x == Q.x && mod(this.y, (byte) 13) == mod((byte) -Q.y, (byte) 13)) return new ECPoint();

        byte lambda;
        if (this.x == Q.x && this.y == Q.y) {
            // P == Q，使用点翻倍公式
            lambda = mod((byte) ((3 * this.x * this.x + a) * modInverse((byte) (2 * this.y), p)), p);
        } else {
            // P != Q，使用普通点加公式
            lambda = mod((byte) ((Q.y - this.y) * modInverse((byte) (Q.x - this.x), p)), p);
        }

        byte x3 = mod((byte) (lambda * lambda - this.x - Q.x), p);
        byte y3 = mod((byte) (lambda * (this.x - x3) - this.y), p);

        return new ECPoint(x3, y3);
    }

    // 标量乘
    public ECPoint mul(int k) {
        ECPoint result = new ECPoint();  // 无穷远点
        ECPoint temp = this;

        while (k > 0) {
            if (k % 2 == 1) {
                result = result.add(temp);
            }
            temp = temp.add(temp);  // 点翻倍
            k >>= 1;
        }
        return result;
    }
}
