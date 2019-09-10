package cn.junengxiong.config.shiro_config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;

public class MyCredentialsMatcher extends HashedCredentialsMatcher {

    /**
     * 重写密码验证器
     * 
     * @param token 用户输入的信息
     * @param info  数据库查询到的信息
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("进入自定义密码比较器");
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String inputName = upt.getUsername();// 用户输入的用户名
        String inputPwd = new String(upt.getPassword());// 用户输入的密码
        String dbPassword = (String) info.getCredentials();// 数据库查询得到的加密后的密码
        // 对用户输入密码进行加密(加密方式,用户输入密码,盐值（用户名）,加密次数)
        String encryptionPwd = new SimpleHash("MD5", inputPwd, inputName, 1024).toString();// 加密后的密码
        return equals(encryptionPwd, dbPassword);
    }

}
