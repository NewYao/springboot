package cn.junengxiong.bean;

public enum Permissions {
    delete("delete"), add("add"), query("query"),modify("modify");
    private String des;
    
    private Permissions(String string)
     
    {
        des=string;
    }
     
    public String GetDes()
    {
         return des;
    }
}
