package cn.junengxiong.bean;

import java.util.Set;

/**
 * 角色bean
 * 
 * @ClassName: Role
 * @Description TODO
 * @version
 * @author JH
 * @date 2020年1月14日 下午2:55:08
 */
public class Role {
    private Integer r_id;
    private String describe;
    private Set<Permission> permissions;

    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

}
