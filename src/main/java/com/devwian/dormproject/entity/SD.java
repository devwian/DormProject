package com.devwian.dormproject.entity;

import lombok.Data;

/**
 * Student-Dorm类
 *
 * @author devwian
 * @date 2021/06/14
 */
@Data
public class SD {
    String studentId;
    String dormId;
    String leader;

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDormId() {
        return dormId;
    }

    public void setDormId(String dormId) {
        this.dormId = dormId;
    }
}
