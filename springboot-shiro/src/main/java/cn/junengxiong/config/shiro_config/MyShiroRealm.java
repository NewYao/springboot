package cn.junengxiong.config.shiro_config;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.junengxiong.bean.User;
import cn.junengxiong.service.UserService;
/**
 * 自定义登录权限认证
 * @ClassName:  MyShiroRealm   
 * @Description TODO
 * @version 
 * @author jh
 * @date 2019年8月27日 下午4:12:40
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {
    
    UserService userService;
    @Autowired
    private void getUserService(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * 权限设置
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("进入自定义权限设置方法！");
        String username = (String)principals.getPrimaryPrincipal();
        //从数据库或换村中获取用户角色信息
        User  user = findByUsername(username);
        //获取用户角色
        Set<String> roles =user.getRole();
        //获取用户权限
        Set<String> permissions = getPermissionsByUserName();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //设置权限
        simpleAuthorizationInfo.setStringPermissions(permissions);
        //设置角色
        simpleAuthorizationInfo.setRoles(roles);

        return simpleAuthorizationInfo;
    }
    /**
     * 所有角色权限对应
     * @return
     */
    private Set<String> getPermissionsByUserName() {
        Set<String> sets = new HashSet<>();
        //sets.add("user:delete");
        sets.add("guest:add");
        sets.add("consumer:query");
        return sets;
    }
    
    /**
     * 身份验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("进入自定义登录验证方法！");
        // 通过username从数据库中查找 User对象，如果找到，没找到.
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        String username = (String) token.getPrincipal();
        //String pwd = token.getCredentials().toString();//获取token中的密码
        User user  = findByUsername(username);
        if(user==null)throw new UnknownAccountException();//用户不存在
        String passwrod = user.getPassword();
        //if(!passwrod.equals(pwd))throw new IncorrectCredentialsException();//凭证不正确
        //主要的，资格证书，区域名称
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, passwrod, "customRealm");
        //加盐
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return simpleAuthenticationInfo;
    }
    
    public User findByUsername(String username) {
        User user = new User();
        user.setPassword(username);
        user.setUsername(username);
        Set<String> roleList = new HashSet<>();
        Set<String> permissionsList = new HashSet<>();
        switch (username) {
        case "admin":
            roleList.add("admin");
            permissionsList.add("admin");
            break;
        case "consumer":
            roleList.add("consumer");
            permissionsList.add("consumer");
            break;
        default:
            roleList.add("guest");
            permissionsList.add("guest");
            break;
        }
        user.setRole(roleList);
        return user;
    }
}