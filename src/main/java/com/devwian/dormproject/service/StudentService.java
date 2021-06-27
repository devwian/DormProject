package com.devwian.dormproject.service;


import com.devwian.dormproject.entity.Student;
import com.devwian.dormproject.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Statement;
import java.util.List;


@Component
@Transactional
public class StudentService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    SDService sdService;

    @Autowired
    DormService dormService;

    //RowMapper<Student> studentRawMapper = new BeanPropertyRowMapper<>(Student.class);

    /**
     * 让学生通过id
     * 获取学生实体
     *
     * @param studentId 学号
     * @return {@link Student}
     */
    public Student getStudentById(String studentId) {
        Student student;
        try {
            student = studentMapper.getStudentById(studentId);
            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> queryAllStudent() {
        return studentMapper.queryAll();
    }


//    public Student getStudentById(String studentId) {
//        Student student;
//        try {
//            logger.info("get student id {}", studentId);
//            student = jdbc.queryForObject("SELECT * FROM studentInfo where studentID = ?", studentRawMapper, new Object[]{studentId});
//            System.out.println(student.getStudentId());
//            logger.info("get student {}", student.getStudentName());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return student;
//    }


    /**
     * 注册学生
     * 学生注册
     *
     * @param studentId 学号
     * @param password  密码
     * @param name      的名字
     * @param tel       电话
     */
    public void registerStudent(String studentId, String password, String name, String tel) {
        logger.info("registering..");
        getStudentById(studentId);
        Student student = new Student(studentId, password, name);
        student.setStudentTel(tel);
        student.setDate(new Date(System.currentTimeMillis()));

        studentMapper.addStudent(student);
    }

    /**
     * 登录学生
     * 学生登录
     *
     * @param studentId 学号
     * @param password  密码
     * @return {@link Student}
     */
    public Student loginStudent(String studentId, String password) {
        logger.info("try to login {} {}", studentId, password);
        Student student = getStudentById(studentId);
        if (student.getStudentPassword().equals(password)) {
            logger.info("login success..");
            return student;
        } else {
            logger.info("wrong password");
            throw new RuntimeException("login filed");
        }
    }

    /**
     * 德尔学生
     * 删除学生信息
     *
     * @param studentId 学号
     * @return int
     */
    public int delStudent(String studentId){
        try{
            studentMapper.delStudent(studentId);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 编辑学生
     * 更改学生信息
     *
     * @param studentId   学号
     * @param studentName 学生的名字
     * @param studentTel  学生的电话
     */
    public void editStudent(String studentId,String studentName,String studentTel){
        logger.info("try edit {} to {}",studentId,studentName);
        try{
            studentMapper.editStudent(studentName,studentTel,studentId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
