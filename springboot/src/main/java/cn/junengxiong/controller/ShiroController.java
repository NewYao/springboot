package cn.junengxiong.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class ShiroController {
    
    @RequiresRoles(value={"admin","consumer"}, logical= Logical.OR)
    @GetMapping("/consumer/{str}")
    public String getMessage(@PathVariable(value="str") String str) {
        return str;
    }
    @RequiresRoles("admin")
    @RequiresPermissions("delete")
    @GetMapping("/admin/{str}")
    public String getMessageAdmin(@PathVariable(value="str") String str) {
        return str;
    }
    
    @GetMapping("/guest/{str}")
    public String getMessageGuest(@PathVariable(value="str") String str) {
        return str;
    }
    
    @PostMapping("/login")
    public String login(String username) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken  token = new UsernamePasswordToken(username,username);
        token.setRememberMe(true);
        subject.login(token);
        return username;
    }
}
