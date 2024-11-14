import ECPoint from "./ECPoint";

export default {
    GenerateKey(APri, BPub) {
        let s1, s2;
        [s1, s2] = ECPoint.scalarMultiply(APri, BPub);
        return s1.toString().padStart(8, '0') + s2.toString().padStart(8, '0');
    }
}