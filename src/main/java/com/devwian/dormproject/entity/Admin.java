package com.devwian.dormproject.entity;

import java.util.Date;

import lombok.Data;

/**
 * 管理
 * adminInfo
 *
 * @author devwian
 * @date 2021/06/20
 */
@Data
public class Admin implements User{
    private String adminId;
    private String adminTel;
    private String adminName;
    private String adminPassword;
    private Date registerAt;
}