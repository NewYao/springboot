package cn.junengxiong.config.shiro_config.realms;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import cn.junengxiong.bean.User;
import cn.junengxiong.config.shiro_config.SpringBeanFactoryUtil;
import cn.junengxiong.service.UserService;

/**
 * 自定义登录权限认证
 * 
 * @ClassName: RealmEmail
 * @Description TODO
 * @version
 * @author jh
 * @date 2019年8月27日 下午4:12:40
 */
public class RealmEmail extends ParentRealm {
    @Autowired
    @Lazy
    UserService userService;

    /**
     * 权限设置
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (userService == null) {
            userService = (UserService) SpringBeanFactoryUtil.getBeanByName("userServiceImpl");
        }
        System.out.println("进入RealmEmail权限设置方法！");
        String username = (String) principals.getPrimaryPrincipal();
        // 从数据库或换村中获取用户角色信息
        User user = userService.findByEmail(username);
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
        System.out.println("进入RealmEmail登录验证方法！");
        if (userService == null) {
            userService = (UserService) SpringBeanFactoryUtil.getBeanByName("userServiceImpl");
        }
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();// 用户输入用户名
        User user = userService.findByEmail(username);// 根据用户输入用户名查询该用户
        if (user == null) {
            throw new UnknownAccountException();// 用户不存在
        }
        if("2".equals(user.getState())) {
            throw new LockedAccountException();
        }
        String password = user.getPassword();// 数据库获取的密码
        // 主要的（用户名，也可以是用户对象（最好不放对象）），资格证书(数据库获取的密码)，区域名称（当前realm名称）
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        // 加盐，对比的时候会使用该参数对用户输入的密码按照密码比较器指定规则加盐，加密，再去对比数据库密文
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return simpleAuthenticationInfo;
    }

}
