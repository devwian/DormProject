package com.devwian.dormproject.controller;

import com.alibaba.fastjson.JSON;
import com.devwian.dormproject.entity.Admin;
import com.devwian.dormproject.service.AdminService;
import com.devwian.dormproject.service.DormService;
import com.devwian.dormproject.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * 管理员相关
 */
@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    StudentService studentService;

    @Autowired
    DormService dormService;

    public static Admin admin;

    final Logger logger = LoggerFactory.getLogger(getClass());
    

    public static final String KEY_USER = "__user__";
    public static final String YES = JSON.toJSONString("YES");;
    public static final String NO = JSON.toJSONString("NO");

    @GetMapping("/admin")
    public ModelAndView admin(HttpSession session) {
        try {
            admin = (Admin) session.getAttribute(KEY_USER);
            if (noLogin()) throw new Exception();
            Map<String, Object> model = new HashMap<>();
            model.put("user", admin);
            return new ModelAndView("admin/admin.peb", model);
        } catch (Exception e) {
            return new ModelAndView("login.peb", Map.of("error", "未登录管理员"));
        }
    }


    /**
     * 没有登录
     * 未登录则返回是
     *
     * @return boolean
     */
    public boolean noLogin() {
        return admin == null;
    }

}
