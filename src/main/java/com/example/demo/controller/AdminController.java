package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.tool.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

    /**
     * 将UserService注入controller层
     */
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ScoreService scoreService;
    //通过教秘的工号，查找教秘可以管理的学生列表
    @GetMapping("/getStudents")
    public CommonResult getStudents(@RequestParam String AdmId)
    {
        Relation[] relations=adminService.selectRelationById(AdmId);
        ArrayList<Student> students=new ArrayList<>();
        if(relations.length>0){
            for(int i=0;i<relations.length;i++){
                Student student = studentService.selectStudentById(relations[i].getStuid());
                students.add(student);
            }
            if(!students.isEmpty())
                 return new CommonResult(200,"success",students);
            else
                return new CommonResult(200,"success",null);
        }
        return CommonResult.validateFailed();
    }
    //查找学院中没有设置班级的学生
    @GetMapping("/getNullClassStudents")
    public CommonResult getNullClassStudents(@RequestParam String college){
        ArrayList<Student> students = studentService.selectStudentByCollege(college);
        ArrayList<Student> nullClassStudents=new ArrayList<>();
        if(!students.isEmpty()){
            for(int i=0;i<students.size();i++){
                if(students.get(i).getClassn()==null){
                    nullClassStudents.add(students.get(i));
                }
            }
            if(!nullClassStudents.isEmpty())
                return new CommonResult(200,"good",nullClassStudents);

        }
        return new CommonResult(200,"bad",null);
    }
    //通过教秘工号，查找可以管理的班级下的学生列表
    @GetMapping("/getTrains")
    public CommonResult getTrains(@RequestParam String AdmId){
        ArrayList<Train> trains = adminService.selectTrainsById(AdmId);
        Map map=new HashMap();
        if(!trains.isEmpty()){
            ArrayList<Integer> nums= adminService.getStudentNums(trains);
            map.put("train",trains);
            map.put("nums",nums);
            return new CommonResult(200,"success",map);
        }
        return CommonResult.validateFailed();
    }
    //查找学院中的各课程的学生列表
    @GetMapping("/getCourses")
    public CommonResult getCourses(@RequestParam String college){
        ArrayList<Course> courses = adminService.selectCoursesByCollege(college);
        Map map=new HashMap();
        if(courses!=null){
            ArrayList<Integer> nums= adminService.getCourseNums(courses);
            map.put("course",courses);
            map.put("nums",nums);
            return new CommonResult(200,"success",map);
        }
        return CommonResult.validateFailed();
    }
    //通过教秘工号和班级查找学生列表
    @GetMapping("/getClassn")
    public CommonResult getClassn(@RequestParam String admid,@RequestParam String classn){
        Admin admin = adminService.selectAdminById(admid);
        if(admin!=null){
            ArrayList<Student> students = studentService.selectStudentByCC(admin.getCollege(), classn);
            if(!students.isEmpty()){
                return new CommonResult(200,"good",students);
            }
            else
                return new CommonResult(200,"bad",null);
        }
        return CommonResult.validateFailed();
    }
    //通过学院和课程去查询没有选当前课程的学生列表
    @GetMapping("/getnullCourseStudents")
    public CommonResult getnullCourseStudents(@RequestParam String college,@RequestParam String course){
        Score[] scores = scoreService.selectScoreByName(course);
        ArrayList<Student> students = studentService.selectStudentByCollege(college);
        ArrayList<String> ids=new ArrayList<>();

        if(!students.isEmpty()){
            if(scores!=null){
                for(int i=0;i<scores.length;i++){
                    String studentid = scores[i].getStudentid();
                    ids.add(studentid);
                }
            }
            if(!ids.isEmpty()){
                for(int i=0;i< ids.size();i++){
                    for(int j=0;j<students.size();j++){
                        if(ids.get(i).equals(students.get(j).getId())){
                            students.remove(j);
                            continue;
                        }
                    }
                }
            }
        }
        if(!students.isEmpty())
            return new CommonResult(200,"good",students);
        return new CommonResult(200,"bad",null);
    }
    //通过课程名和任教老师查找学生列表
    @GetMapping("/getCourse")
    public CommonResult getCourse(@RequestParam String name,@RequestParam String teacher){
        ArrayList<Student> students = adminService.selectStudentsByTeaName(teacher, name);
        if(!students.isEmpty())
            return new CommonResult(200,"good",students);
        return new CommonResult(200,"bad",null);
    }
    //通过教秘工号查询关系表数据
    @GetMapping("/getRelations")
    public CommonResult getRelations(@RequestParam String AdmId)
    {
        Relation[] relations=adminService.selectRelationById(AdmId);
        ArrayList<String> stunames=new ArrayList<>();
        ArrayList<String> teanames=new ArrayList<>();
        ArrayList<String> allteanames=new ArrayList<>();
        Admin admin = adminService.selectAdminById(AdmId);
        ArrayList<Teacher> teachers = adminService.selectTeacherByCollege(admin.getCollege());
        Map map=new HashMap();
        if(relations.length>0){
            for(int i=0;i<relations.length;i++){
                Student student = studentService.selectStudentById(relations[i].getStuid());
                stunames.add(student.getName());
                if(relations[i].getTeaid()!=null) {
                    Teacher teacher=teacherService.selectTeacherById(relations[i].getTeaid());
                    teanames.add(teacher.getName());
                }
                else teanames.add("-1");
            }
            for(int i=0;i<teachers.size();i++){
                allteanames.add(teachers.get(i).getName());
            }
            if(!stunames.isEmpty()){
                map.put("stunames",stunames);
                map.put("teanames",teanames);
                map.put("allteanames",allteanames);
                map.put("relations",relations);
                return new CommonResult(200,"success",map);
            }

            else
                return new CommonResult(200,"success",null);
        }
        return CommonResult.validateFailed();
    }
    //通过学号和课程名退课
    @PostMapping("/removeCourse")
    public CommonResult removeCourse(@RequestParam String id,@RequestParam String name){
        int num = adminService.removeCourse(id, name);
        if(num>0)
            return new CommonResult(200,"good",null);
        return CommonResult.validateFailed();
    }
    //通过学号，课程名，任课老师添加课程
    @PostMapping("/addCourse")
    public CommonResult addCourse(@RequestParam String stuid,@RequestParam String name,@RequestParam String teacher){
        int num = adminService.addCourse(stuid, name, teacher);
        if(num>0)
            return new CommonResult(200,"good",null);
        return new CommonResult(200,"bad",null);
    }
    //通过课程，容量，任课老师修改课程信息
    @PostMapping("/modifyCourse")
    public CommonResult modifyCourse(@RequestParam String name,@RequestParam String nums,@RequestParam String teacher){

        int num=adminService.modifyCourse(name,teacher,Integer.parseInt(nums));
        if(num>0)
            return new CommonResult(200,"good",null);
        return CommonResult.validateFailed();
    }
    //通过专业班级添加学生
    @PostMapping("/addClassn")
    public CommonResult addClassn(@RequestParam String classn,@RequestParam String stuid,@RequestParam String major){
        int num=adminService.addClassn(stuid,classn,major);
        if(num>0)
            return new CommonResult(200,"good",null);
        return new CommonResult(200,"bad",null);
    }
    //给学生退课
    @PostMapping("/removeClassn")
    public CommonResult removeClassn(@RequestBody Student student){
        int num = adminService.removeClassn(student);
        if(num>0)
            return new CommonResult(200,"good",null);
        return CommonResult.validateFailed();
    }
    //通过班级工号容量修改班级容量
    @PostMapping("/modifyTrains")
    public CommonResult modifyTrains(@RequestParam String classn,@RequestParam String num,@RequestParam String admid){
        int nums=0;
        ArrayList<Train> trains = adminService.selectTrainsById(admid);
        if(!trains.isEmpty()){
            for(int i=0;i<trains.size();i++){
                if(trains.get(i).getClassn().equals(classn))
                {
                    Train train=trains.get(i);
                    train.setNum(Integer.parseInt(num));
                    nums+=adminService.modifyTrain(train);
                    if(nums>0)
                         return new CommonResult(200,"success",null);
                    break;
                }
            }
        }

        return CommonResult.validateFailed();
    }
    //配置导师
    @PostMapping("/modifyRelations")
    public CommonResult modifyRelations(@RequestParam String stuid,@RequestParam String teaname,@RequestParam String admid){
        int num=0;
        Teacher teacher = teacherService.selectTeacherByName(teaname);
        if(teacher!=null){
            Relation relation=new Relation(stuid,teacher.getId(),admid);
            num+=adminService.modifyRelation(relation);
        }
        if(num>0)
            return new CommonResult(200,"success",null);
        return CommonResult.validateFailed();
    }
    //修改学生
    @PostMapping("/modifyStudents")
    public CommonResult modifyStudents(@RequestBody Student student,@RequestParam String oldid){
        int num=0;
        if(student!=null){
            num+=adminService.modifyStudentById(student,oldid);
        }
        if(num>0)
            return new CommonResult(200,"success",null);
        return CommonResult.validateFailed();
    }
    //删除学生
    @PostMapping("/deleteStudents")
    public CommonResult deleteStudents(@RequestParam String id){
        int num=0;
        if(id!=null){
            num+=adminService.deleteStudents(id);
        }
        if(num>0)
            return new CommonResult(200,"success",null);
        return CommonResult.validateFailed();
    }
    //添加学生
    @PostMapping("/addStudents")
    public CommonResult addStudents(@RequestBody Student student,@RequestParam String id){
        int num=0;
        if(student!=null){
            num+=adminService.insertStudents(student,id);
        }
        if(num==-1){
            return new CommonResult(200,"bad",null);
        }
        else if(num>0){
            return new CommonResult(200,"good",null);
        }
        return CommonResult.validateFailed();
    }

}
