package com.gxu.tbvp.controller;



import com.gxu.tbvp.domain.Manager;
import com.gxu.tbvp.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by zqw.
 * twodog.
 */
@Controller
public class HomeController {
    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){
        return "login";
    }

    // 以下RequestMapping临时测试前端使用
/*    @RequestMapping(value = "/table", method = RequestMethod.GET)
    public String table() { return "table"; }

    @RequestMapping(value="/visitos",method= RequestMethod.GET)
    public String visitos(){
        return "visitos";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products() { return "products"; }

    @RequestMapping(value = "/agent", method = RequestMethod.GET)
    public String agent() { return "agent"; }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() { return "index"; }*/
    //ending

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(HttpServletRequest request, Manager manager, Model model){
        if (StringUtils.isEmpty(manager.getUsername()) || StringUtils.isEmpty(manager.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(manager.getUsername(),manager.getPassword());
        try {
            subject.login(token);
            return "index";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }

    @RequestMapping(value={"/usersPage",""})
    public String usersPage(){
        return "index";
    }

    @RequestMapping("/rolesPage")
    public String rolesPage(){
        return "role/roles";
    }

    @RequestMapping("/resourcesPage")
    public String resourcesPage(){
        return "resources/resources";
    }

    @RequestMapping("/403")
    public String forbidden(){
        return "403";
    }
}
