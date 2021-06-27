package com.devwian.dormproject.controller;


import com.alibaba.fastjson.JSON;
import com.devwian.dormproject.entity.Admin;
import com.devwian.dormproject.entity.SD;
import com.devwian.dormproject.mapper.DormMapper;
import com.devwian.dormproject.mapper.StudentMapper;
import com.devwian.dormproject.service.SDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * SDController
 *
 * @author devwian
 * @date 2021/06/14
 */
@Controller
public class SDController {

    @Autowired
    SDService sdService;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    DormMapper dormMapper;
    private static Admin admin;
    private static final String KEY_USER = "__user__";
    private static final String YES = JSON.toJSONString("YES");
    private static final String NO = JSON.toJSONString("NO");

    /**
     * sd
     * 住宿信息管理页面
     *
     * @param session 会话
     * @return {@link ModelAndView}
     */
    @GetMapping("/admin/student-dorm")
    public ModelAndView sd(HttpSession session){
        admin = (Admin) session.getAttribute(KEY_USER);
        if (noLogin())
            return new ModelAndView("/login.peb", Map.of("error","未登录管理员"));
        return new ModelAndView("/admin/student-dorm.peb",Map.of("user",admin));
    }

    /**
     * 表格获取住宿信息
     * @return 住宿信息
     */
    @GetMapping("/getSD")
    @ResponseBody
    public List<SD> getSD(HttpSession session){
        //未登录则不返回信息，但不一定需要是管理员
        try{
            Admin admin = (Admin) session.getAttribute(KEY_USER);
            if (admin==null){
                throw new Exception();
            }
            return sdService.getSD();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 添加sd
     * 添加住宿信息
     *
     * @param dormId    宿舍id
     * @param studentId 学号
     * @return {@link String}
     */
    @PostMapping("/admin/addSD")
    @ResponseBody
    public String addSD(String dormId, String studentId){
        if (noLogin())
            return NO;
        try{
            switch (sdService.addSD(dormId, studentId)) {
                case "true":
                    return YES;
                case "full":
                    return JSON.toJSONString("FULL");
                case "null":
                    return JSON.toJSONString("NULL");
                default:
                    return NO;
            }
        }catch (Exception e){
            e.printStackTrace();
            return NO;
        }

    }

    /**
     * 删除住宿信息
     *
     * @param studentId 学生信息
     * @return {@link String}
     */
    @PostMapping("/admin/delSD")
    @ResponseBody
    public String delSD(String studentId){
        if (noLogin())
            return NO;
        try{
            if(sdService.delSD(studentId))
                return YES;
            else return NO;
        }catch (Exception e){
            e.printStackTrace();
            return NO;
        }

    }

    /**
     * 编辑sd
     *
     * @param studentId 学号
     * @param dormId    宿舍id
     * @param leader    舍长
     * @return {@link String}
     */
    @PostMapping("/editSD")
    @ResponseBody
    public String editSD(String studentId,String dormId,String leader){
        if (noLogin())
            return NO;
        SD sd = new SD();
        sd.setStudentId(studentId);
        sd.setDormId(dormId);
        if (leader!=null)
            sd.setLeader(leader);
        if (sdService.editSD(sd)){
            return YES;
        }else
            return NO;
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
