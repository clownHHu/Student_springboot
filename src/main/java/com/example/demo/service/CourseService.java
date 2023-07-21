package com.example.demo.service;


import com.example.demo.model.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {

    Course selectCourseByTN(String term, String name);//通过学期和课程名查找课程
    Course[] selectCourseByTerm(String term);//通过学期查找课程列表
    Map<String,String[][]> getDataByCourse(Course course[]);//通过课程列表查询课程数据
    Map<String,String[][]> selectDataByCourse(List<Course[]> course,boolean flag);//选课
    Course[] selectCourseByName(String name);//通过姓名查询课程列表
    Course selectCourseByCourseName(String name);//通过课程名查询课程
    int modifyContext(Course course);//修改课程大纲

}