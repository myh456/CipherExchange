import CryptoJS from 'crypto-js';

export default {
    //加密
    enc(word, keyStr, vectorInit) {
        var key = CryptoJS.enc.Utf8.parse(keyStr);
        var srcs = CryptoJS.enc.Utf8.parse(word);
        var iv = CryptoJS.enc.Utf8.parse(vectorInit);
        var encrypted = CryptoJS.AES.encrypt(srcs, key, { mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv });
        return encrypted.toString();
    },

    //解密
    dec(word, keyStr, vectorInit) {
        var key = CryptoJS.enc.Utf8.parse(keyStr);
        var iv = CryptoJS.enc.Utf8.parse(vectorInit);
        var decrypt = CryptoJS.AES.decrypt(word, key, { mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv });
        return CryptoJS.enc.Utf8.stringify(decrypt).toString();
    }
}