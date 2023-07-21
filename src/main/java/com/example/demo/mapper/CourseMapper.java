package com.example.demo.mapper;

import com.example.demo.model.Course;
import org.apache.ibatis.annotations.Param;

public interface CourseMapper {

    Course selectCourseByTN(String term,String name);
    Course[] selectCourseByTerm(String term);
    Course[] selectCourseByName(String name);
    Course selectCourseByCourseName(String name);
    int modifyContext(@Param(value ="course") Course course);
    Course selectCourseByTeaName(String teacher, String name);
}
