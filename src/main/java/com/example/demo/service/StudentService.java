package com.example.demo.service;

import com.example.demo.model.Achieve;
import com.example.demo.model.Student;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public interface StudentService {

    Student bulidStudentByRequest(HttpServletRequest request);//添加学生
    Student selectStudentById(String id);//查找学生
    int modifyStudent(String id,String name,String sex,String college,String classn,String phone,
                      String major,String idnumber,String date,
                      String nativeplace,String polstatus,String location,String remark,String avatar);//修改学生信息
    int modifyAvatarById(String path,String id);//修改头像
    String uploadAvatar(MultipartFile file) throws IOException;//上传头像
    boolean compareStu(Student stu1,Student stu2);//对比学生信息
    String uploadAchieve(MultipartFile file) throws IOException;//上传科研信息
    Achieve[] selectAchieveById(String id);//根据学号获取科研信息列表
    ArrayList<Student> selectStudentByCC(String college,String classn);//根据学院班级获取学生列表
    ArrayList<Student> selectStudentByCollege(String college);//根据学院获取学生列表
}