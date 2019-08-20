package cn.junengxiong.bean;

public enum Role {
    admin("admin"), guest("guest"), consumer("consumer");
    private String des;
    
    private Role(String string)
     
    {
        des=string;
    }
     
    public String GetDes()
    {
         return des;
    }
}
