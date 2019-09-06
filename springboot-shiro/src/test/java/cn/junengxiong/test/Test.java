package cn.junengxiong.test;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Test {
    @org.junit.Test
    public void t1() {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "guest";//密码原值
        ByteSource salt = ByteSource.Util.bytes("guest");//以账号作为盐值
        int hashIterations = 1024;//加密次数
        Object result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);
        System.out.println("admin:"+result);
    }
}
