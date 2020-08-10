package com.shop.api;


import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;


public class TestRSA {
    @Test
    public void test1(){
        RSA rsa = new RSA();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();//私钥
        String publicKeyBase64 = rsa.getPublicKeyBase64();//公钥
        System.out.println(privateKeyBase64);
        System.out.println(publicKeyBase64);
    }
    @Test
    public void text2() throws UnsupportedEncodingException {
        String publicKey="text1打印的公钥";
        String privateKey ="test1打印的私钥";
        RSA rsa = new RSA(privateKey,publicKey);//注意方法中的第一个参数为私钥
        String result = rsa.encryptBase64("lisi", KeyType.PublicKey);//即通过公钥将lisi这个明文数据进行加密
        System.out.println("加密后"+result);
        byte[] res = rsa.decryptFromBase64(result, KeyType.PrivateKey);//即通过私钥对密文进行解密，并返回字节数组。
        //若不涉及到中文则下面方法的第二个参数可不写,
        //该方法的作用即为将字节转为字符串
        String into = new String(res, "utf-8");//此处抛异常，try catch 也行
        System.out.println("解密后"+into);//右击运行，可打印  解密后:lisi  至此即证明之前的理论正确
    }



}
