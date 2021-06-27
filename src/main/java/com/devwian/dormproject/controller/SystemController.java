package com.devwian.dormproject.controller;

import com.devwian.dormproject.entity.Admin;
import com.devwian.dormproject.entity.Student;
import com.devwian.dormproject.service.AdminService;
import com.devwian.dormproject.service.SDService;
import com.devwian.dormproject.service.StudentService;
import com.devwian.dormproject.utils.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SystemController {

    @Autowired
    StudentService studentService;

    @Autowired
    AdminService adminService;

    @Autowired
    SDService sdService;

    final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String KEY_USER = "__user__";


    /**
     * 主页
     *
     * @param session 会话
     * @return {@link ModelAndView}
     */
    @GetMapping("/")
    public ModelAndView index(HttpSession session) {
        if (session.getAttribute("Type") != null) {
            if (session.getAttribute("Type").equals("student")) {
                try {
                    Student student = (Student) session.getAttribute(KEY_USER);
                    Map<String, Object> model = new HashMap<>();
                    model.put("user", student);
                    model.put("student",student);
                    return new ModelAndView("index.peb", model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (session.getAttribute("Type").equals("admin")) {
                try {
                    Admin admin = (Admin) session.getAttribute(KEY_USER);
                    Map<String, Object> model = new HashMap<>();
                    model.put("user", admin);
                    return new ModelAndView("index.peb", model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new ModelAndView("index.peb");
    }

    /**
     * 登录界面
     *
     * @param session 会话
     * @return {@link ModelAndView}
     */
    @GetMapping("/login")
    public ModelAndView login(HttpSession session) {

        if (session.getAttribute("user") != null) {
            return new ModelAndView("redirect:/profile");
        }
        return new ModelAndView("login.peb");
    }

    @PostMapping("/login")
    public ModelAndView doLogin(
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            @RequestParam("type") String type,
            HttpSession session) {
        if (type.equals("student")) {
            try {
                Student student = studentService.loginStudent(id, password);
                session.setAttribute(KEY_USER, student);
                session.setAttribute("Type", "student");

            } catch (RuntimeException e) {
                e.printStackTrace();
                return new ModelAndView("login.peb", Map.of("user", id, "error", "登录失败"));
            }
        } else if (type.equals("admin")) {
            try {
                Admin admin = adminService.loginAdmin(id, password);
                session.setAttribute(KEY_USER, admin);
                session.setAttribute("Type", "admin");
                //System.out.println(session.getAttribute("Type"));
                return new ModelAndView("redirect:/admin");
            } catch (RuntimeException e) {
                e.printStackTrace();
                return new ModelAndView("login.peb", Map.of("user", id, "error", "登录失败"));
            }
        } else {
            return new ModelAndView("login.peb", Map.of("user", id, "error", "登录失败"));
        }

        return new ModelAndView("redirect:/student");
    }


    /**
     * 注册界面
     *
     * @return
     */
    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register.peb");
    }

    @PostMapping("/register")
    public ModelAndView doRegister(
            @RequestParam("id") String id
            , @RequestParam("password") String password
            , @RequestParam("name") String name
            , @RequestParam("type") String type
            , @RequestParam("tel") String tel) {

        try {
            //合法用户名判断
            if (!RegexUtils.idJudge(id)) {
                return new ModelAndView("/register.peb", Map.of("user", id, "error", RegexUtils.getReason()));
            }
            if (name.isEmpty()) {
                return new ModelAndView("/register.peb", Map.of("user", id, "error", "姓名不能空！"));
            }
            //合法密码判断
            if (!RegexUtils.pwdJudge(password)) {
                return new ModelAndView("/register.peb", Map.of("user", password, "error", RegexUtils.getReason()));
            }
            //电话号码判断
            if (!RegexUtils.telJudge(tel)) {
                return new ModelAndView("/register.peb", Map.of("user", tel, "error", RegexUtils.getReason()));
            }
            //学生注册
            if (type.equals("student")) {
                studentService.registerStudent(id, password, name, tel);
            }
            //宿管注册
            else if (type.equals("admin")) {
                adminService.registerAdmin(id, password, name, tel);
            }
        } catch (RuntimeException e) {
            logger.info("register field");
            return new ModelAndView("/register.peb", Map.of("user", id, "error", "注册失败！"));
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpSession session) {

        if (session.getAttribute("Type").equals("student")) {
            Student student = (Student) session.getAttribute(KEY_USER);
            return new ModelAndView("profile.peb",
                    Map.of("user", student, "Type", "student","dorm",
                            sdService.getDormIdByStudentId(student.getStudentId())));

        } else if (session.getAttribute("Type").equals("admin")) {
            Admin admin = (Admin) session.getAttribute(KEY_USER);
            return new ModelAndView("profile.peb", Map.of("user", admin, "Type", "admin"));
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.setAttribute(KEY_USER,null);
        session.invalidate();
        return new ModelAndView("redirect:/");
    }
}
