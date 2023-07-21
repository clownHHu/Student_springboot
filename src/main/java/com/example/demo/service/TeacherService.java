package com.example.demo.service;


import com.example.demo.model.Achieve;
import com.example.demo.model.Relation;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface TeacherService {
    Teacher selectTeacherById(String id);//通过工号查询学生
    Teacher bulidTeacherByRequest(HttpServletRequest request);//添加老师
    int modifyAvatarById(String path,String id);//修改头像
    String uploadAvatar(MultipartFile file) throws IOException;//上传头像
    boolean compareTea(Teacher tea1,Teacher tea2);//对比教师信息是否相同
    int modifyTeacher(Teacher teacher);//修改教师信息
    Relation[] selectRelationById(String id);//通过工号获取关系表
    Achieve selectAchieveByIT(String id, String time);//获取科研信息
    int insertAchieve(Achieve achieve);//插入科研信息
    Teacher selectTeacherByName(String name);//通过姓名查找教师
}
