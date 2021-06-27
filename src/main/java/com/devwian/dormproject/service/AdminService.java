package com.devwian.dormproject.service;

import com.devwian.dormproject.entity.Admin;
import com.devwian.dormproject.mapper.AdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Component
@Transactional
public class AdminService {

    @Autowired
    AdminMapper adminMapper;


    final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 从数据库获取admin
     *
     * @param id
     * @return
     */
    public Admin getAdminById(String id) {
        Admin admin;
        try {
            admin = adminMapper.getAdminById(id);
            return admin;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证admin密码
     *
     * @param id
     * @param pwd
     * @return
     */
    public Admin loginAdmin(String id, String pwd) {
        Admin admin;
        admin = getAdminById(id);
        logger.info("try to login admin {}", id);
        if (admin.getAdminPassword().equals(pwd)) {
            return admin;
        } else {
            throw new RuntimeException("login field");
        }
    }

    /**
     * 管理员注册
     *
     * @param id
     * @param pwd
     * @param name
     * @param tel
     */
    public void registerAdmin(String id, String pwd, String name, String tel) {
        logger.info("admin register {}  {}", id, name);
        Admin admin = new Admin();
        admin.setAdminId(id);
        admin.setAdminPassword(pwd);
        admin.setAdminTel(tel);
        admin.setAdminName(name);
        admin.setRegisterAt(new Date(System.currentTimeMillis()));

        adminMapper.insertInto(admin);
    }
}
