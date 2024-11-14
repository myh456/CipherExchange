import ECPoint from "./ECPoint";

export default {
    subKey(G) {
        let d = Math.floor(Math.random() * 97 + 1);
        return [d, ECPoint.scalarMultiply(d, G)]
    }
}