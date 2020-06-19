package cn.jnx.bean;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Member {
	// 编号
	@ExcelProperty(index = 0)
	private Integer persionNo;

	// 姓名
	@ExcelProperty(value = { "姓名" })
	private String name;

	@ExcelProperty(value = { "工号" })
	private String jobNo;

	@ExcelProperty(value = { "性别" })
	private String sex;

	@ExcelProperty(value = { "出生日期" })
	@DateTimeFormat(value = "yyyy/MM/dd") // excel读取时对应的表格内格式
	@JSONField(format = "yyyy-MM-dd") // json输出格式化
	private Date birthday;

	@ExcelProperty(value = { "身份证号码" })
	private String idCard;

	public Integer getPersionNo() {
		return persionNo;
	}

	public void setPersionNo(Integer persionNo) {
		this.persionNo = persionNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
