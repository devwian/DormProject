package com.devwian.dormproject.controller;

import com.alibaba.fastjson.JSON;
import com.devwian.dormproject.entity.Admin;
import com.devwian.dormproject.entity.Dorm;
import com.devwian.dormproject.service.DormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DormController {

    @Autowired
    DormService dormService;

    static Admin admin;

    final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String KEY_USER = "__user__";
    public static final String YES = JSON.toJSONString("YES");;
    public static final String NO = JSON.toJSONString("NO");

    /**
     * 管理宿舍信息
     * @return
     */
    @GetMapping("/admin/managedorminfo")
    public ModelAndView dormInfo(HttpSession session) {
        //logger.info("manageDorm");
        try {
            admin = (Admin) session.getAttribute(KEY_USER);
            if (noLogin()) throw new Exception();

            Map<String, Object> model = new HashMap<>();

            model.put("user", admin);
            return new ModelAndView("admin/dorm.peb", model);
        } catch (Exception e) {
            return new ModelAndView("login.peb", Map.of("error", "未登录管理员"));
        }
    }

    /**
     * 添加宿舍
     * 添加宿舍信息
     *
     * @param dormId    宿舍id
     * @param dormNum   宿舍num
     * @param dormTel   宿舍电话
     * @param className 班级名
     * @return {@link String}
     */
    @PostMapping("/admin/addDorm")
    @ResponseBody
    public String addDorm(String dormId,String dormNum,String dormTel,String className){
        try{
            if (noLogin()) throw new Exception();
            int dormNum1 = Integer.parseInt(dormNum);

            dormService.addDorm(dormId,dormNum1,dormTel,className);
            return YES;
        }catch (Exception e){
            e.printStackTrace();
            return NO;
        }
    }

    /**
     * 得到宿舍
     * 获取宿舍信息
     *
     * @return {@link List<Dorm>}
     */
    @GetMapping("/admin/getDorm")
    @ResponseBody
    public List<Dorm> getDorm(){
        try{
            if (noLogin()) throw new Exception();
            return dormService.queryAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 编辑宿舍
     * 更改宿舍信息
     *
     * @param dormId    宿舍id
     * @param dormNum   宿舍num
     * @param dormTel   宿舍电话
     * @param className 类名
     * @param session   会话
     * @return {@link String}
     */
    @PostMapping("/admin/editDorm")
    @ResponseBody
    public String editDorm(String dormId, String dormNum, String dormTel, String className, HttpSession session){
        try{
            if (noLogin()) throw new Exception();
            Dorm dorm = new Dorm();

            dorm.setDormId(dormId);
            dorm.setDormNum(Integer.parseInt(dormNum));
            dorm.setDormTel(dormTel);
            dorm.setClassName(className);

            dormService.editDorm(dorm);
            return YES;
        }catch (Exception e){
            e.printStackTrace();
            return NO;
        }
    }

    /**
     * 德尔宿舍
     * 删除宿舍
     *
     * @param dormId 宿舍id
     * @return {@link String}
     */
    @PostMapping("/admin/delDorm")
    @ResponseBody
    public String delDorm(String dormId){
        try{
            if (noLogin()) throw new Exception();
            dormService.delDorm(dormId);
            return YES;
        }catch (Exception e){
            e.printStackTrace();
            return NO;
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
