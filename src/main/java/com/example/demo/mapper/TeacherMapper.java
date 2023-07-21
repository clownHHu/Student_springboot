package com.example.demo.mapper;


import com.example.demo.model.Achieve;
import com.example.demo.model.Relation;
import com.example.demo.model.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper {
    Teacher selectTeacherById(String id);
    int modifyAvatarById(String path,String id);
    int modifyTeacher(@Param(value = "teacher")Teacher teacher);
    Relation[] selectRelationById(String id);
    Achieve selectAchieveByIT(String id, String time);
    int insertAchieve(@Param("achieve") Achieve achieve);
    Teacher selectTeacherByName(String name);
}
