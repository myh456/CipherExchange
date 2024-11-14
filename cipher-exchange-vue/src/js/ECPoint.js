export default {
    // 求模
    mod(a, m) {
        let res = a % m;
        if (res < 0)
            res += m;
        return res
    },
    // 求模反元素
    modInverse(a, m) {
        let m0 = m, t, q;
        let x0 = 0, x1 = 1;
        if (m === 1) return 0;
        while (a > 1) {
            q = Math.floor(a / m);
            t = m;
            m = a % m;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }
        if (x1 < 0) x1 += m0;
        return x1;
    },

    // 点加法
    pointAdd(P, Q) {
        // 如果P或Q为无穷远点
        if (P === null) return Q;
        if (Q === null) return P;
        // 如果Q是P的逆，返回无穷远点
        if (P[0] === Q[0] && this.mod(P[1], 13) === this.mod(-Q[1], 13)) return null;

        let [x1, y1] = P;
        let [x2, y2] = Q;

        let lambda;

        if (P[0] === Q[0] && P[1] === Q[1]) {
            // 点倍加
            lambda = (3 * x1 * x1 + 1) * this.mod(this.modInverse(2 * y1, 13), 13);
        } else {
            // 两点相加
            lambda = (y2 - y1) * this.mod(this.modInverse(x2 - x1, 13), 13);
        }

        let x3 = this.mod(lambda * lambda - x1 - x2, 13);
        let y3 = this.mod(lambda * (x1 - x3) - y1, 13);

        return [x3, y3];
    },

    // 标量乘法
    scalarMultiply(k, P) {
        let result = null; // 无穷远点
        let addend = P;

        while (k > 0) {
            if (k % 2 === 1) {
                result = this.pointAdd(result, addend);
            }
            addend = this.pointAdd(addend, addend);
            k <<= 1;
        }

        return result;
    },
}