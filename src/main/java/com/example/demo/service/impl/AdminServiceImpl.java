package com.example.demo.service.impl;

import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.ScoreMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.*;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminServiceImpl implements AdminService {
    /**
     * 注入mapper到service层
     */
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public Admin selectAdminById(String id) {
        return adminMapper.selectAdminById(id);
    }
    @Override
    public Relation[] selectRelationById(String id) {
        return adminMapper.selectRelationById(id);
    }
    @Override
    public int modifyStudentById(Student student,String oldid){
        int num=0;
        if(student.getId().equals(oldid)){
            num+=adminMapper.modifyStudent(student);
        }
        else {
            num+=adminMapper.modifyRelationById(student,oldid);
            num+=adminMapper.modifyStudentId(student,oldid);
            num+=adminMapper.modifyAchStudentId(student,oldid);
            num+=adminMapper.modifyLogStudentId(student,oldid);
        }
        return num;
    }

    @Override
    public int deleteStudents(String id) {
        int num=0;
        num+=adminMapper.deleteRelationById(id);
        num+=adminMapper.deleteLoginById(id);
        return num;
    }

    @Override
    public ArrayList<Teacher> selectTeacherByCollege(String college){
        return adminMapper.selectTeacherByCollege(college);
    }
    @Override
    public ArrayList<Course> selectCoursesByCollege(String college){
        return adminMapper.selectCoursesByCollege(college);
    }
    @Override
    public ArrayList<Integer> getCourseNums(ArrayList<Course> courses){
        ArrayList<Integer> nums=new ArrayList<>();
        for(int i=0;i<courses.size();i++){
            ArrayList<Score> scores = adminMapper.selectScoresByCourse(courses.get(i).getName());
            nums.add(scores.size());
        }
        return nums;
    }@Override
    public ArrayList<Student> selectStudentsByTeaName(String teacher,String name){
        Course course = courseMapper.selectCourseByTeaName(teacher, name);
        ArrayList<Score> scores = adminMapper.selectScoresByCourse(course.getName());
        ArrayList<Student> students=new ArrayList<>();
        if(!scores.isEmpty()){
            for(int i=0;i<scores.size();i++)
            {
                Student student = studentMapper.selectStudentById(scores.get(i).getStudentid());
                students.add(student);
            }
        }
        return students;
    }
    @Override
    public ArrayList<Integer> getStudentNums(ArrayList<Train> trains){
        ArrayList<Integer> nums=new ArrayList<>();

        for(int i=0;i<trains.size();i++){
            ArrayList<Student> students=adminMapper.selectStudentsByTrain(trains.get(i));
            nums.add(students.size());
        }
        return nums;
    }
    @Override
    public int modifyRelation(Relation relation){
        return adminMapper.modifyRelation(relation);
    }
    @Override
    public int modifyTrain(Train train){
        return adminMapper.modifyTrain(train);
    }
    @Override
    public int removeClassn(Student student){
        return adminMapper.removeClassn(student);
    }
    @Override
    public int removeCourse(String id,String name){
        return adminMapper.removeCourse(id,name);
    }
    @Override
    public int addClassn(String id,String classn,String major){
        Train train = adminMapper.selectTrainsByCM(classn, major);
        return adminMapper.addClassn(id,train);
    }
    @Override
    public int modifyCourse(String name,String teacher,int nums){
        return adminMapper.modifyCourse(name,teacher,nums);
    }
    @Override
    public int addCourse(String stuid,String name,String teacher){
        Course course = courseMapper.selectCourseByTeaName(teacher, name);
        Score score=new Score(stuid,course.getTerm(),"0",course.getName());
        return adminMapper.addCourse(score);
    }
    @Override
    public ArrayList<Train> selectTrainsById(String id){
        return adminMapper.selectTrainsById(id);
    }
    @Override
    public int insertStudents(Student students, String AdmId){
        int num=0;
        ArrayList<Relation> relations = adminMapper.selectRelations();
        ArrayList<Login> logins = adminMapper.selectLoginsByStatus("1");
        ArrayList<Student> students1 = adminMapper.selectStudents();
        if(!relations.isEmpty()){
            for(int i=0;i<relations.size();i++){
                if(relations.get(i).getStuid().equals(students.getId())){
                    return -1;
                }
            }
            Relation relation=new Relation(students.getId(),null,AdmId);
            num+=adminMapper.insertRelation(relation);
        }
        if(!logins.isEmpty()){
            for(int i=0;i<logins.size();i++){
                if(logins.get(i).getId().equals(students.getId())){
                    return -1;
                }
            }
            Login login=new Login(students.getId(),students.getId(),1);
            num+=adminMapper.insertLogin(login);
        }
        if(!students1.isEmpty()){
            for(int i=0;i<students1.size();i++){
                if(students1.get(i).getId().equals(students.getId())){
                    return num;
                }
            }
            Student student=new Student();
            student.setId(students.getId());
            student.setName(students.getName());
            student.setCollege(students.getCollege());

            num+=adminMapper.insertStudent(student);
        }
        return num;
    }

}
