package com.example.demo.mapper;

import com.example.demo.model.Achieve;
import com.example.demo.model.Student;

import java.util.ArrayList;

public interface StudentMapper {
    Student selectStudentById(String id);
    Achieve[] selectAchieveById(String id);
    ArrayList<Student> selectStudentByCC(String college,String classn);
    ArrayList<Student> selectStudentByCollege(String college);
    int modifyAvatarById(String path,String id);
    int modifyStudent(String id,String name,String sex,String college,String classn,String phone,
                      String major,String idnumber,String date, String nativeplace,String polstatus,String location,String remark,String avatar);
}
