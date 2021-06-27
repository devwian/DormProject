package com.devwian.dormproject.mapper;

import com.devwian.dormproject.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
    /**
     * 数据库获取学生实体
     *
     * @param studentId
     * studentId
     * @return
     * student
     */
    Student getStudentById(String studentId);

    boolean addStudent(Student student);

    List<Student> queryAll();

    boolean delStudent(String studentId);

    boolean editStudent(String studentName,String studentTel,String studentId);
}
