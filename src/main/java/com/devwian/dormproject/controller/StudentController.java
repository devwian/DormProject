package com.devwian.dormproject.controller;


import com.alibaba.fastjson.JSON;
import com.devwian.dormproject.entity.Admin;
import com.devwian.dormproject.entity.Student;
import com.devwian.dormproject.service.DormService;
import com.devwian.dormproject.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class StudentController {


    @Autowired
    StudentService studentService;


    static Admin admin;

    final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String KEY_USER = "__user__";
    public static final String YES = JSON.toJSONString("YES");
    public static final String NO = JSON.toJSONString("NO");


    @RequestMapping("/student")
    public ModelAndView student(HttpSession session){
        try{
            Student student = (Student) session.getAttribute(KEY_USER);
            if (student.getStudentId()==null){
                throw new Exception();
            }
            Map<String,Object> map = new HashMap<>();
            map.put("user",student);
            return new ModelAndView("/student/student.peb",map);
        }catch (Exception e){
            return new ModelAndView("login.peb",Map.of("error","未登录"));
        }
    }

    /**
     * 学生信息
     * 学生信息管理页面
     *
     * @param session 会话
     * @return {@link ModelAndView}
     */
    @GetMapping("/admin/managestuinfo")
    public ModelAndView studentInfo(HttpSession session) {
        try {
            admin = (Admin) session.getAttribute(KEY_USER);
            if (noLogin()) throw new Exception();

            Map<String, Object> model = new HashMap<>();
            List<Student> list = studentService.queryAllStudent();

            model.put("stuInfo", list);
            return new ModelAndView("admin/student.peb", model);
        } catch (Exception e) {
            return new ModelAndView("login.peb", Map.of("error", "未登录管理员"));
        }
    }


    /**
     * 返回学生信息
     *
     * @return {@link List<Student>}
     */
    @GetMapping("/admin/getStudent")
    @ResponseBody
    public List<Student> getStudent() {
        try{
            if (noLogin()) throw new Exception();
            return studentService.queryAllStudent();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 编辑学生
     * 编辑学生信息
     *
     * @param studentId   学号
     * @param studentName 学生的名字
     * @param studentTel  学生的电话
     * @return {@link String}
     */
    @PostMapping("/admin/editStudent")
    @ResponseBody
    public String editStudent(String studentId, String studentName, String studentTel){
        try {
            if (noLogin()) throw new Exception();

            studentService.editStudent(studentId,studentName,studentTel);
            return YES;
        }catch (Exception e){
            e.printStackTrace();
            return NO;
        }
    }

    /**
     * 德尔学生
     * 删除学生
     *
     * @param studentId 学号
     * @return {@link String}
     */
    @PostMapping("/admin/delStudent")
    @ResponseBody
    public String delStudent(String studentId){
        try{

            if (noLogin()) throw new Exception();

            logger.info("{} delete {}",admin.getAdminId(),studentId);
            if (studentService.delStudent(studentId)==0)
                return NO;
            return YES ;
        }catch (Exception e){
            e.printStackTrace();
            return NO;
        }
    }

    /**
     * 添加学生
     *
     * @param studentId   学号
     * @param studentName 学生的名字
     * @param studentTel  学生的电话
     * @param session     会话
     * @return {@link String}
     */
    @PostMapping("/admin/addStudent")
    @ResponseBody
    public String addStudent(String studentId,String studentName,String studentTel,HttpSession session){
        try{
            Admin admin = (Admin) session.getAttribute(KEY_USER);
            if (noLogin()) throw new Exception();

            studentService.registerStudent(studentId,"123456",studentName,studentTel);

            logger.info("{} add student {} {}",admin.getAdminId(),studentId,studentName);
            return JSON.toJSONString("YES") ;
        }catch (Exception e){
            e.printStackTrace();
            return NO;
        }
    }

    /**
     * 学生宿舍
     *
     * @param session 会话
     * @return {@link ModelAndView}
     */
    @GetMapping("/student/mydorm")
    public ModelAndView studentDorm(HttpSession session){
        Student student = (Student) session.getAttribute("user");
        return new ModelAndView("/student/studentDorm.peb");
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
