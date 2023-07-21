package com.example.demo.controller;
import com.example.demo.model.*;
import com.example.demo.service.CourseService;
import com.example.demo.service.ScoreService;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
import com.example.demo.tool.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {


    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private StudentService studentService;
    //修改教师信息
    @PostMapping("/modifyTeacher")
    public CommonResult modifyTeacher(@RequestBody MultipartFile file, HttpServletRequest request) throws IOException {
        Teacher teacher = teacherService.bulidTeacherByRequest(request);
        Teacher teacher1 = teacherService.selectTeacherById(teacher.getId());
        if(teacher1.getAvatar()==null)//第一次登陆，赋予默认头像
        {
            teacherService.modifyAvatarById("image/avatar.jpg",teacher1.getId());
            teacher1.setAvatar("image/avatar.jpg");
        }
        boolean flag=true;
        String path=null;
        if(file!=null)
            path = teacherService.uploadAvatar(file);
        if (path != null) {
            teacher.setAvatar(path);
        }
        else teacher.setAvatar(teacher1.getAvatar());

        if(teacherService.compareTea(teacher,teacher1))
            flag=false;
        if(flag==true) {
            int num = teacherService.modifyTeacher(teacher);
            if (num > 0)
                return new CommonResult(200, "good", teacher);
            else
                return CommonResult.validateFailed();
        }
        else return new CommonResult(200,"bad",teacher1);
    }
    //获取教师任教课程
    @GetMapping("/checkCourse")
    public CommonResult checkCourse(@RequestParam String name)
    {
        Course courses[]=courseService.selectCourseByName(name);
        if(courses.length>0)
        {
            return new CommonResult(200,"success",courses);
        }
        return CommonResult.validateFailed();
    }
    //修改老师任教课程的教学大纲
    @PostMapping("/changeCourse")
    public CommonResult changeCourse(@RequestParam String name,@RequestParam String context)
    {
        Course course=courseService.selectCourseByCourseName(name);
        if(course.getContext().equals(context))
            return new CommonResult(200,"bad",null);
        course.setContext(context);
        if(course!=null)
        {
            if(courseService.modifyContext(course)>0)
                return new CommonResult(200,"good",null);
        }
        return CommonResult.validateFailed();
    }
    //查看学生课程分数
    @GetMapping("/checkScore")
    public CommonResult checkScore(@RequestParam String name)
    {
        Score score[]=scoreService.selectScoreByName(name);
        if(score.length>0)
        {
            ScoreByCourse scores[]=new ScoreByCourse[score.length];
            for(int i=0;i<score.length;i++)
            {
                Student stu=studentService.selectStudentById(score[i].getStudentid());
                ScoreByCourse score1=new ScoreByCourse(stu.getId(),stu.getName(),score[i].getScore());
                scores[i]=score1;
            }
            return new CommonResult(200,"success",scores);
        }
        return CommonResult.validateFailed();
    }
    //根据学号，提交时间处理科研流程
    @PostMapping("/handleAchieve")
    public CommonResult handleAchieve(@RequestParam String id,@RequestParam String time,@RequestParam String flag)
    {

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowtime=dateFormat.format(date);
        Achieve achieve = teacherService.selectAchieveByIT(id, time);
        achieve.setState(Integer.parseInt(flag));
        achieve.setTime(nowtime);
        if(achieve!=null){
            int num = teacherService.insertAchieve(achieve);
            if(num>0)
                if(flag.equals("1"))
                    return new CommonResult(200,"good",null);
                else
                    return new CommonResult(200,"bad",null);
        }
        return CommonResult.validateFailed();
    }
    //获取学生提交的科研信息
    @GetMapping("/checkAchieve")
    public CommonResult checkAchieve(@RequestParam String TeacherId)
    {
        Relation relation[]=teacherService.selectRelationById(TeacherId);
        ArrayList<Achieve> achieves=new ArrayList<>();
        if(relation!=null){
            for(int i=0;i<relation.length;i++)
            {
                Achieve achieve[]=studentService.selectAchieveById(relation[i].getStuid());
                if(achieve!=null){
                    for(int j=0;j<achieve.length;j++) {
                        achieve[j].setName(studentService.selectStudentById(achieve[j].getId()).getName());
                        if(achieve[j].getState()==0) {
                            achieves.add(achieve[j]);
                        }
                        else {
                            for(int k=0;k<achieves.size();k++){
                                if(achieves.get(k).getId().equals(achieve[j].getId())&&achieves.get(k).getAchid().equals(achieve[j].getAchid())){
                                    achieves.get(k).setState(achieve[j].getState());
                                    achieves.get(k).setTime(achieve[j].getTime());
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if(achieves.size()>0)
                return new CommonResult(200,"good",achieves);
            else return new CommonResult(200,"bad",null);
        }

        return CommonResult.validateFailed();
    }
    //修改学生分数
    @PostMapping(value = "changeScore")
    public CommonResult changeScore(@RequestBody Score[] StudentData)
    {
        boolean flag=false;
        if(StudentData.length>0)
        {
            for(int i=0;i<StudentData.length;i++)
            {
                Score score=scoreService.selectScoreByNI(StudentData[i].getStudentid(),StudentData[i].getCoursename());
                if(score!=null)
                {
                    if(!score.getScore().equals(StudentData[i].getScore()))
                    {
                        score.setScore(StudentData[i].getScore());
                        if(scoreService.modifyScore(score)>0)
                            flag=true;
                    }

                }
            }
            if(flag)
                return new CommonResult(200,"good",null);
            else return new CommonResult(200,"bad",null);
        }
        return CommonResult.validateFailed();
    }


}