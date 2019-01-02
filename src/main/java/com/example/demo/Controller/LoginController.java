package com.example.demo.Controller;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录系统
 */
@Controller
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private UserService userService;


    @RequestMapping("/")
    public String index(HttpServletRequest request) throws Exception{

        return "redirect:" + request.getContextPath() + "/login";
    }

    @RequestMapping("/login")
    public String login() throws Exception{
        return "/login";
    }

    @RequestMapping("/admin")
    public String admin(Model model) throws Exception{
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");
        model.addAttribute("user_name",user.getNickName());
        model.addAttribute("user_pic",user.getUserPic());
        return "/index";
    }

    @RequestMapping("/logout")
    public String loginOut(){
        try{
            SecurityUtils.getSubject().logout();
        }catch (Exception e){
            System.out.printf("退出失败！");
        }
        return "/login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public Map doLogin(String user_name, String password,int flag){

        Map map = new HashMap<>();

        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        boolean remeberMe = false;
        if(flag == 1){
            remeberMe = true;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user_name,password,remeberMe);
        try{
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(token);
        } catch (Exception e) {
            map.put("code",1001);
            map.put("message","登录失败");
            return map;
        }

        Session session = subject.getSession();
        session.setAttribute("user",userService.getUserByName(user_name));

        map.put("code",1000);
        map.put("message","登录成功");
        return map;
    }

    


}
