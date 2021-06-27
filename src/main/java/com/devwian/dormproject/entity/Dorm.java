package com.devwian.dormproject.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 宿舍
 * dormInfo
 *
 * @author devwian
 * @date 2021/06/14
 */
@Data
public class Dorm implements Serializable {
    private String dormId;

    private Integer dormNum;

    private String dormTel;

    private String className;

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    private static final long serialVersionUID = 1L;

    public String getDormId() {
        return dormId;
    }

    public void setDormId(String dormId) {
        this.dormId = dormId;
    }

    public Integer getDormNum() {
        return dormNum;
    }

    public void setDormNum(Integer dormNum) {
        this.dormNum = dormNum;
    }

    public String getDormTel() {
        return dormTel;
    }

    public void setDormTel(String dormTel) {
        this.dormTel = dormTel;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


}