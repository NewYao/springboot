package cn.junengxiong.bean;

/**
 * 权限bean
 * 
 * @ClassName: Permission
 * @Description TODO
 * @version
 * @author JH
 * @date 2020年1月14日 下午2:54:58
 */
public class Permission {
    private Integer p_id;
    private String describe;

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
