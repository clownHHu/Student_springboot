package com.example.demo.service;


import com.example.demo.model.*;

import java.util.ArrayList;

public interface AdminService {
    Admin selectAdminById(String id);//根据工号查找教秘
    Relation[] selectRelationById(String id);//根据工号查找关系列表
    int modifyStudentById(Student student,String oldid);//修改学生信息
    int deleteStudents(String id);//删除学生
    int insertStudents(Student student, String AdmId);//插入学生
    int modifyRelation(Relation relation);//修改关系表
    int modifyTrain(Train train);//修改学员信息
    int removeClassn(Student student);//删除班级中的某学生
    int removeCourse(String id,String name);//删除课程中的某学生
    int addClassn(String id,String classn,String major);//向班级添加学生
    int modifyCourse(String name,String teacher,int nums);//修改课程容量
    int addCourse(String stuid,String name,String teacher);//给学生添加课程
    ArrayList<Train> selectTrainsById(String id);//通过工号查询学院信息表
    ArrayList<Teacher> selectTeacherByCollege(String college);//查询学院的老师
    ArrayList<Integer> getStudentNums(ArrayList<Train> trains);//查看班级内的学生人数
    ArrayList<Course> selectCoursesByCollege(String college);//查看学院的课程
    ArrayList<Integer> getCourseNums(ArrayList<Course> courses);//查看课程容量
    ArrayList<Student> selectStudentsByTeaName(String teacher,String name);//通过老师和课程查找选课的学生列表

}