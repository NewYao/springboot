package cn.jnx.bean;

import com.alibaba.excel.annotation.ExcelProperty;

public class Gqsj {

    private Integer id;
    @ExcelProperty(value = { "企业名称（旧）" })
    private String old_name;
    @ExcelProperty(value = { "企业名称（新）" })
    private String new_name;
    @ExcelProperty(value = { "纳税识别号/统一社会信用代码【修改后的】" })
    private String corp_code;
    @ExcelProperty(value = { "认定年份批次" })
    private String patch_year;
    @ExcelProperty(value = { "拥有I类知识产权（发明、集成电路布图设计、新药证书、新品种等）-协同" })
    private Integer intellectual_property;
    @ExcelProperty(index = 6)
    private Integer software_copyright;
    @ExcelProperty(value = { "近三年科技成果转化数-协同" })
    private Integer convert;
    @ExcelProperty(value = { "其中科技成果是专利-协同" })
    private Integer patent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOld_name() {
        return old_name;
    }

    public void setOld_name(String old_name) {
        this.old_name = old_name;
    }

    public String getNew_name() {
        return new_name;
    }

    public void setNew_name(String new_name) {
        this.new_name = new_name;
    }

    public String getCorp_code() {
        return corp_code;
    }

    public void setCorp_code(String corp_code) {
        this.corp_code = corp_code;
    }

    public String getPatch_year() {
        return patch_year;
    }

    public void setPatch_year(String patch_year) {
        this.patch_year = patch_year;
    }

    public Integer getIntellectual_property() {
        return intellectual_property;
    }

    public void setIntellectual_property(Integer intellectual_property) {
        this.intellectual_property = intellectual_property;
    }


    public Integer getSoftware_copyright() {
        return software_copyright;
    }

    public void setSoftware_copyright(Integer software_copyright) {
        this.software_copyright = software_copyright;
    }

    public Integer getConvert() {
        return convert;
    }

    public void setConvert(Integer convert) {
        this.convert = convert;
    }

    public Integer getPatent() {
        return patent;
    }

    public void setPatent(Integer patent) {
        this.patent = patent;
    }

}
