package cn.junengxiong.config.shiro_config.realms;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 统一重写realm中清除缓存方法
 * 
 * @ClassName: RealmsParents
 * @Description TODO
 * @version
 * @author jh
 * @date 2020年1月6日 下午4:20:25
 */
public abstract class ParentRealm extends AuthorizingRealm {

    /**
     * 重写方法,清除当前用户的的 授权缓存
     * 
     * @param principals
     */
    public void clearCachedAuthorizationInfo() {
        super.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * 
     * @param principals
     */
    public void clearCachedAuthenticationInfo() {
        super.clearCachedAuthenticationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 清除某个用户认证和授权缓存
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的 认证缓存 和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
