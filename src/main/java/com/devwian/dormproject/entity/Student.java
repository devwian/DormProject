package com.devwian.dormproject.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Student implements User{
    private String studentId;
    private String studentName;
    private String studentTel;
    private String studentPassword;

    private Date date;

    public Student() {

    }

    public Student(String studentId, String password, String name) {
        this.studentId = studentId;
        this.studentPassword = password;
        this.studentName = name;
    }

    public String getStudentTel() {
        return studentTel;
    }

    public void setStudentTel(String studentTel) {
        this.studentTel = studentTel;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
