package cn.junengxiong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class ShiroController {
        
    @GetMapping("/message/{str}")
    public String getMessage(@PathVariable(value="str") String str) {
        return str;
    }
    
    @GetMapping("/admin/{str}")
    public String getMessageAdmin(@PathVariable(value="str") String str) {
        return str;
    }
    
    @GetMapping("/guest/{str}")
    public String getMessageGuest(@PathVariable(value="str") String str) {
        return str;
    }
}
