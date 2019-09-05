package cn.junengxiong.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.junengxiong.bean.ReturnMap;

@Controller
@RestController
public class ShiroController {

	@RequestMapping("/consumer/{str}")
	@RequiresRoles(value = { "admin", "consumer" }, logical = Logical.OR)
	@RequiresPermissions(value = { "consumer:all", "admin:all" }, logical = Logical.OR)
	public ReturnMap getMessage(@PathVariable(value = "str") String str) {
		return new ReturnMap().success().data(str);
	}

	@RequestMapping("/admin/{str}")
	@RequiresRoles("admin")
	@RequiresPermissions("admin:all")
	public ReturnMap getMessageAdmin(@PathVariable(value = "str") String str) {
		return new ReturnMap().success().data(str);
	}

	@RequestMapping("/guest/{str}")
	public ReturnMap getMessageGuest(@PathVariable(value = "str") String str) {
		return new ReturnMap().success().data(str);
	}

	@RequestMapping("/login")
	public ReturnMap login(String username, @Value("false") Boolean rememberMe, String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe);
		try {
			// 登录
			subject.login(token);
		} catch (UnknownAccountException uae) {
			// 用户名未知...
			return new ReturnMap().fail().message("用户不存在！");
		} catch (IncorrectCredentialsException ice) {
			// 凭据不正确，例如密码不正确 ...
			return new ReturnMap().fail().message("密码不正确！");
		} catch (LockedAccountException lae) {
			// 用户被锁定，例如管理员把某个用户禁用...
			return new ReturnMap().fail().message("用户被锁定！");
		} catch (ExcessiveAttemptsException eae) {
			// 尝试认证次数多余系统指定次数 ...
			return new ReturnMap().fail().message("尝试认证次数过多，请稍后重试！");
		} catch (AuthenticationException ae) {
			// 其他未指定异常
			return new ReturnMap().fail().message("未知异常！");
		}
		return new ReturnMap().success().data("登录成功！");
	}

	@RequestMapping("/loginout")
	public ReturnMap getMessageGuest() {
		Subject subject = SecurityUtils.getSubject();
		// 登出
		subject.logout();
		return new ReturnMap().success().message("登出成功！");
	}

	/**
	 * 无权限访问时
	 * 
	 * @return
	 */
	@RequestMapping("/unauthorized")
	public ReturnMap unauthorized() {
		return new ReturnMap().invalid();
	}
}
