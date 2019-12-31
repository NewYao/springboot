package cn.junengxiong.config.shiro_config;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 
 * @ClassName: MyUsernamePasswordToken
 * @Description 自定义用户名密码凭证类，携带登录类型信息
 * @version
 * @author JH
 * @date 2019年12月31日 下午2:11:40
 */
public class MyUsernamePasswordToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 1L;
    //登录类型
    private String loginType;

    public MyUsernamePasswordToken(String username, final String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

}
