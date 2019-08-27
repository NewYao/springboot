package cn.junengxiong.bean;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created with Eclipse
 *
 * @Author JH
 * @Description 接口返回对象
 * @Date 2018-02-26
 * @Time 16:03
 */
@Component
public class ReturnMap extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReturnMap() {
	    
	}

	/**
	 * 成功
	 * 
	 * @return
	 */
	public ReturnMap success() {
		this.put("result", "success");
		this.put("code", 200);
		return this;
	}

	/**
	 * 失败
	 * 
	 * @return
	 */
	public ReturnMap fail() {
		this.put("result", "fail");
		this.put("code", 400);
		return this;
	}

	/**
	 * 无权限
	 * 
	 * @param code
	 * @return
	 */
	public ReturnMap invalid() {
		this.put("result", "invalid");
		this.put("code", 403);
		return this;
	}

	/**
	 * 服务器错误
	 * 
	 * @param code
	 * @return
	 */
	public ReturnMap error() {
		this.put("code", "error");
		this.put("code", 500);
		return this;
	}

	/**
	 * 描述
	 * 
	 * @param message
	 * @return
	 */
	public ReturnMap message(String s) {
		this.put("message", s);
		return this;
	}

	/**
	 * 简单数据数据
	 * 
	 * @param message
	 * @return
	 */
	public ReturnMap data(String s, Object v) {
		this.put(s, v);
		return this;
	}

	/**
	 * 数据
	 * 
	 * @param message
	 * @return
	 */
	public ReturnMap data(Object o) {
		this.put("data", o);
		return this;
	}
}
