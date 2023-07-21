package com.example.demo.mapper;
import com.example.demo.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface AdminMapper {
    Admin selectAdminById(String id);
    Relation[] selectRelationById(String id);
    int modifyStudent(@Param(value="student")Student student);
    int modifyRelationById(@Param(value="student")Student student,String oldid);
    int modifyStudentId(@Param(value="student")Student student,String oldid);
    int modifyAchStudentId(@Param(value="student")Student student,String oldid);
    int modifyLogStudentId(@Param(value="student")Student student,String oldid);
    int modifyRelation(@Param(value="relation")Relation relation);
    int modifyTrain(@Param(value="train")Train train);
    int modifyCourse(String name,String teacher,int nums);
    int addClassn(String id,@Param(value = "train")Train train);
    int removeClassn(@Param(value="student")Student student);
    int removeCourse(String id,String name);
    int insertLogin(@Param(value="login")Login login);
    int insertRelation(@Param(value="relation")Relation relation);
    int insertStudent(@Param(value="student")Student student);
    int addCourse(@Param(value="score")Score score);
    Train selectTrainsByCM(String classn,String major);
    ArrayList<Login> selectLoginsByStatus(String status);
    ArrayList<Relation> selectRelations();
    ArrayList<Student> selectStudents();
    ArrayList<Teacher> selectTeacherByCollege(String college);
    ArrayList<Train> selectTrainsById(String id);
    ArrayList<Student> selectStudentsByTrain(@Param(value="train")Train train);
    ArrayList<Course> selectCoursesByCollege(String college);
    ArrayList<Score> selectScoresByCourse(String coursename);
    int deleteRelationById(String id);
    int deleteLoginById(String id);
}
