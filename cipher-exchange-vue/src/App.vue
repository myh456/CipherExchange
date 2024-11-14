<template>
  <div id="app">
    密钥：<label>{{ sharedKey }}</label> <br />
    明文：<label>{{ plain }}</label> <br />
    ECDH：<br />
    <button @click="requestPub">生成密钥</button> <br />
    <button @click="requestMsg">接收消息</button>
  </div>
</template>

<script>
import axios from "axios";
import ECC from "@/js/ECC";
import ECDH from "./js/ECDH";
import AESUtil from "./js/AESUtil";

export default {
  data: () => {
    return {
      d: 0,
      G: [5, 1],
      pubKey: [0, 0],
      sharedKey: "",
      plain: "",
    };
  },
  methods: {
    requestPub: function () {
      try {
        [this.d, this.pubKey] = ECC.subKey(this.G);
        axios({
          method: "get",
          params: {
            x: this.pubKey[0],
            y: this.pubKey[1],
          },
          baseURL: "http://localhost:8080",
          url: "/ecdh/requestPub",
        })
          .then((msg) => {
            this.sharedKey = ECDH.GenerateKey(this.d, [
              msg.data.data.x,
              msg.data.data.y,
            ]);
          })
          .catch((err) => {
            console.error(err);
          });
      } catch (e) {
        this.requestPub();
      }
    },
    requestMsg: function () {
      axios({
        method: "get",
        baseURL: "http://localhost:8080",
        url: "/ecdh/requestMsg",
      })
        .then((msg) => {
          let iv = msg.data.data.substr(0, 16);
          this.plain = AESUtil.dec(msg.data.data.slice(16), this.sharedKey, iv);
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
};
</script>

<style>
</style>
