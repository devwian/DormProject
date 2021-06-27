package com.devwian.dormproject.controller;

import com.alibaba.fastjson.JSON;
import com.devwian.dormproject.entity.Admin;
import com.devwian.dormproject.entity.Property;
import com.devwian.dormproject.entity.Student;
import com.devwian.dormproject.service.PropertyService;
import com.devwian.dormproject.service.SDService;
import com.devwian.dormproject.service.StudentService;
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
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @Autowired
    StudentService studentService;

    @Autowired
    SDService sdService;

    final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String KEY_USER = "__user__";
    public static final String YES = JSON.toJSONString("YES");
    public static final String NO = JSON.toJSONString("NO");

    static Student student;
    static Admin admin;


    @GetMapping("/student/property")
    public ModelAndView studentProperty(HttpSession session){
        try{
            student = (Student) session.getAttribute(KEY_USER);
            if (noLogin())
                throw new Exception();

            Map<String, Object> model = new HashMap<>();

            model.put("user",student);
            return new ModelAndView("/student/property.peb",model);
        }catch (Exception e){
            return new ModelAndView("/login.peb",Map.of("error","未登录到学生"));
        }
    }

    @GetMapping("/admin/property")
    public ModelAndView adminProperty(HttpSession session){
        try{
            admin = (Admin) session.getAttribute(KEY_USER);
            if (noLogin())
                throw new Exception();
            Map<String,Object> map = new HashMap<>();
            map.put("user",admin);
            return new ModelAndView("admin/property.peb",map);
        }catch (Exception e){
            return new ModelAndView("/login.peb",Map.of("error","未登录到管理员"));
        }
    }

    /**
     * 插入财产
     *
     * @param propertyId   财产id
     * @param propertyName 财产名
     * @return {@link String}
     */
    @PostMapping("/addProperty")
    @ResponseBody
    public String insertProperty(String propertyId,String propertyName,String dormId,HttpSession session){
        try {
            if (noLogin()) throw new Exception();

            Property property = new Property();
            property.setPropertyId(propertyId);
            property.setPropertyName(propertyName);
            //以学生插入宿舍财产
            if (session.getAttribute("Type").equals("student")){
                property.setDormId(sdService.getDormIdByStudentId(student.getStudentId()));

            }//否则以管理员插入财产信息
            else {
                property.setDormId(dormId);
            }
            int code = propertyService.insertProperty(property);
            if(code==9){
                return JSON.toJSONString("EXIST") ;
            }else if(code==1){
                return NO;
            }
            return YES;
        }catch (Exception e){
            return NO;
        }
    }

    /**
     * 德尔财产
     *
     * @param propertyId 属性id
     * @return {@link String}
     */
    @PostMapping("/delProperty")
    @ResponseBody
    public String delProperty(String propertyId){
        if (noLogin())
            return NO;
        if (propertyService.delProperty(propertyId)){
            return YES;
        }else {
            return NO;
        }
    }

    /**
     * 查询属性
     *
     * @return {@link List<Property>}
     */
    @GetMapping("/getProperty")
    @ResponseBody
    public List<Property> queryProperty(HttpSession session){
        if (noLogin())
            return null;
        if(session.getAttribute("Type").equals("admin")){
            return propertyService.queryProperty();
        }else{
            return propertyService.studentGetProperty(student.getStudentId());
        }
    }

    @PostMapping("/editProperty")
    @ResponseBody
    public String editProperty(String propertyId,String propertyName){
        Property property = new Property();
        property.setPropertyId(propertyId);
        property.setPropertyName(propertyName);
        if (propertyService.editProperty(property)){
            return YES;
        }else {
            return NO;
        }
    }
    public boolean noLogin() {
        return ((admin == null)&&(student == null));
    }
}
