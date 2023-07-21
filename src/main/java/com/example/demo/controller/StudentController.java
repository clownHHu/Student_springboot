package com.example.demo.controller;
import com.example.demo.config.WebConfig;
import com.example.demo.model.*;
import com.example.demo.service.AchieveService;
import com.example.demo.service.CourseService;
import com.example.demo.service.ScoreService;
import com.example.demo.service.StudentService;
import com.example.demo.tool.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/student")
public class StudentController {
    /**
     * 将StudentService注入controller层
     */
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private AchieveService achieveService;

    //修改学生信息
    @PostMapping("/modifyStudent")
     public CommonResult modifyStudent(@RequestBody MultipartFile file,HttpServletRequest request) throws IOException {
        Student student = studentService.bulidStudentByRequest(request);
        Student student1=studentService.selectStudentById(student.getId());
        if(student1.getAvatar()==null)//第一次登陆，赋予默认头像
        {
            studentService.modifyAvatarById("image/avatar.jpg",student1.getId());
            student1.setAvatar("image/avatar.jpg");
        }
        boolean flag=true;
        String path=null;
        if(file!=null)
            path = studentService.uploadAvatar(file);
        if (path != null) {
            student.setAvatar(path);
        }
        else student.setAvatar(student1.getAvatar());

        if(studentService.compareStu(student,student1))
            flag=false;
        if(flag==true) {
        int num = studentService.modifyStudent(student.getId(), student.getName(), student.getSex(), student.getCollege(), student.getClassn(),
                student.getPhone(), student.getMajor(), student.getIdnumber(), student.getDate(),
                student.getNativeplace(), student.getPolstatus(), student.getLocation(), student.getRemark(), student.getAvatar());
        if (num > 0)
            return new CommonResult(200, "good", student);
        else
            return CommonResult.validateFailed();
        }
        else return new CommonResult(200,"bad",student1);
    }
    //上传附件并登记科研信息
    @PostMapping("/Achieve")
    public CommonResult insertStudentAchieve(@RequestBody MultipartFile file,@RequestParam String id,@RequestParam String context) throws IOException {

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowtime=dateFormat.format(date);
        String path=studentService.uploadAchieve(file);
        int state;
        if(path!=null) {
            state=0;
            Achieve achieve=new Achieve(id,nowtime,context,state,path);
            Achieve achieves[]=achieveService.getAllAchieve(id);
            if(achieves!=null){
                int index=0;
                for(int i=0;i<achieves.length;i++){
                    if(achieves[i].getState()==0)
                        index++;
                }
                achieve.setAchid(String.valueOf(index));
            }
            if(achieveService.insertAchieve(achieve)>0)
                return new CommonResult(200,"good",null);
        }
        return new CommonResult(200,"bad",null);
    }
    //获取科研信息登记记录
    @PostMapping("/GetAchieve")
    public CommonResult getStudentAchieve(@RequestBody Achieve achieve)
    {
        Achieve achieves[]=achieveService.getAllAchieve(achieve.getId());
        if(achieves.length>0)
        {
            if(achieves[achieves.length-1].getState()==0)
                return new CommonResult(200,"good",achieves);
            else
                return new CommonResult(200,"bad",achieves);
        }
        return CommonResult.validateFailed();
    }
    //通过学号查看培养方案
    @PostMapping("/checkPyfa")
    public CommonResult checkStudentPyfa(@RequestBody Student stu)
    {
        String id=stu.getId();
        Student student=studentService.selectStudentById(id);
        if(student!=null)
        {
            return new CommonResult(200,"success",student.getTrain());
        }
        return CommonResult.validateFailed();
    }
    //通过学号和学期查看所选课程
    @PostMapping("/checkCourse")
    public CommonResult checkStudentCourse(@RequestParam String term,@RequestParam String id)
    {

        Score score[]=scoreService.selectScoreByTI(id,term);
        Course courses[] = null;
        if(score.length>0)
        {
            courses = new Course[score.length];
            for(int i=0;i<score.length;i++)
            {
                Course course=courseService.selectCourseByTN(score[i].getCourseterm(),score[i].getCoursename());
                courses[i]=course;
            }
        }
//        Course course[]=courseService.selectCourseByTerm(term);
        Map<String,String[][]> data=new HashMap<>();
        if(courses!=null)
            data=courseService.getDataByCourse(courses);
        if(!data.isEmpty())
            return new CommonResult(200,"success",data);
        return new CommonResult(200,"success",null);
    }
    //通过学号和学期查看成绩
    @PostMapping("/checkGrade")
    public CommonResult checkStudentGrade(@RequestParam String id,@RequestParam String term)
    {

        Score score[];
        Map<String, List<String>>data = new HashMap<>();
        if(term.equals("成绩总览"))
            score=scoreService.getAllScore(id);
        else
            score=scoreService.selectScoreByTI(id,term);
        if(score.length>0)
        {
            data=scoreService.getDataByScore(score);
            if(!data.isEmpty())
                return new CommonResult(200,"success",data);
        }
        else return new CommonResult(200,"success",null);
        return CommonResult.validateFailed();
    }

    //根据学号和学期查看可选课程
    @PostMapping("/selectCourse")
    public CommonResult selectStudentCourse(@RequestParam String term,@RequestParam String id)
    {
        Score score[]=scoreService.selectScoreByTI(id,term);
        Course courses[] = null;
        Course othercourses[]=null;
        if(score.length>0)
        {
            courses = new Course[score.length];
            for(int i=0;i<score.length;i++)
            {
                Course course=courseService.selectCourseByTN(score[i].getCourseterm(),score[i].getCoursename());
                courses[i]=course;
            }
        }
        Course termcourse[]=courseService.selectCourseByTerm(term);
        if(termcourse!=null)
            if(termcourse.length>(courses==null?0:courses.length))
            {
                int j;
                int otherindex=0;
                othercourses=new Course[termcourse.length-(courses==null?0:courses.length)];
                for(int i=0;i<termcourse.length;i++)
                {
                    for(j=0;j<(courses==null?0:courses.length);j++)
                    {
                        if(termcourse[i].getName().equals(courses[j].getName()))
                            break;
                    }
                    if(j==(courses==null?0:courses.length))
                        othercourses[otherindex++]=termcourse[i];
                }
            }
        List<Course[]> listCourse=new ArrayList<>();
        boolean flag=false;
        Map<String,String[][]> datalist=new HashMap<>();
        if(courses!=null)
            listCourse.add(courses);
        else
            flag=true;
        if(othercourses!=null)
            listCourse.add(othercourses);

        if(!listCourse.isEmpty())
            datalist=courseService.selectDataByCourse(listCourse,flag);
        if(!listCourse.isEmpty())
            return new CommonResult(200,"listCourse",datalist);
        return new CommonResult(200,"success",null);
    }
    //通过学号学期课程退课选课
    @PostMapping("/changeCourse")
    public CommonResult changeStudentCourse(@RequestParam String term,@RequestParam String id,@RequestParam String coursename)
    {

        Score score=scoreService.selectScoreByNI(id,coursename);
        if(score!=null)
        {
            if(Integer.parseInt(score.getScore())>0)
                return new CommonResult(200,"bad",null);
            else
            {
                //删除
                if(scoreService.deleteScoreByNI(id,coursename)>0)
                    return new CommonResult(200,"good",null);
            }
        }
        else
        {
            if(!coursename.equals("")) {
                Score score1 = new Score(id, term, "0", coursename);
                if (scoreService.insertScore(score1) > 0)
                    return new CommonResult(200, "add", null);
            }
        }
        return CommonResult.validateFailed();
    }

}


