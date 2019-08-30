package cn.junengxiong.config.shiro_config;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.junengxiong.bean.User;
import cn.junengxiong.service.UserService;

/**
 * 自定义登录权限认证
 * 
 * @ClassName: MyShiroRealm
 * @Description TODO
 * @version
 * @author jh
 * @date 2019年8月27日 下午4:12:40
 */

public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    /**
     * 权限设置
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (userService == null) {
            userService = (UserService) SpringBeanFactoryUtil.getBeanByName("userServiceImpl");
        }
        System.out.println("进入自定义权限设置方法！");
        String username = (String) principals.getPrimaryPrincipal();
        // 从数据库或换村中获取用户角色信息
        User user = userService.findByUsername(username);
        // 获取用户角色
        Set<String> roles = user.getRole();
        // 获取用户权限
        Set<String> permissions = user.getPermission();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 设置权限
        simpleAuthorizationInfo.setStringPermissions(permissions);
        // 设置角色
        simpleAuthorizationInfo.setRoles(roles);

        return simpleAuthorizationInfo;
    }

    /**
     * 身份验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("进入自定义登录验证方法！");
        if (userService == null) {
            userService = (UserService) SpringBeanFactoryUtil.getBeanByName("userServiceImpl");
        }
        // 通过username从数据库中查找 User对象，如果找到，没找到.
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String pwd = usernamePasswordToken.getPassword().toString();//获取用户输入的密码
        User user = userService.findByUsername(username);
        if (user == null)
            throw new UnknownAccountException();// 用户不存在
        String password = user.getPassword();// 数据库获取的密码
        //此处对用户输入密码进行加密，对比数据库查询出来加密后的密码，自定义加密方式
        //............................
        if(!password.equals(pwd)) {
            throw new IncorrectCredentialsException();// 凭证错误
        }
        // 主要的（可以使用户名，也可以是用户对象），资格证书(数据库获取的密码)，区域名称（当前realm名称）
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        // 加盐,使用每个用户各自的用户名加盐，保证密码相同时但是加密后密码仍然不同,如果不适用shiro自带凭证比较器，可以不设置加盐（个人猜想）
        //simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return simpleAuthenticationInfo;
    }

}
