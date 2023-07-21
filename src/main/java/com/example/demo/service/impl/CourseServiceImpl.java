package com.example.demo.service.impl;

import com.example.demo.mapper.CourseMapper;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import com.example.demo.tool.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {
    /**
     * 注入mapper到service层
     */
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Course selectCourseByTN(String term, String name) {
        return courseMapper.selectCourseByTN(term, name);
    }

    @Override
    public Course[] selectCourseByTerm(String term) {
        return courseMapper.selectCourseByTerm(term);
    }

    @Override
    public Map<String, String[][]> getDataByCourse(Course course[]) {
        String courses[][] = new String[7][13];
        String contexts[][] = new String[7][13];
        Map<String, String[][]> data = new HashMap<>();
        for (int i = 0; i < course.length; i++) {
            for (int j = Integer.parseInt(course[i].getBepos()); j <= Integer.parseInt(course[i].getEnpos()); j++)
                if (courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] == null && j == Integer.parseInt(course[i].getBepos())) {
                    courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] = course[i].getName() +
                            "\t任课:" + course[i].getTeacher() +
                            "\t时间:第" + course[i].getBegin() + "周-第" + course[i].getEnd() + "周\t课时1";
                    contexts[Integer.parseInt(course[i].getDay()) - 1][j - 1] = course[i].getName() + ":" + course[i].getContext();
                } else if (courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] != null && j == Integer.parseInt(course[i].getBepos())) {
                    courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] += "//" + course[i].getName() +
                            "\t任课:" + course[i].getTeacher() +
                            "\t时间:第" + course[i].getBegin() + "周-第" + course[i].getEnd() + "周\t课时1";
                    contexts[Integer.parseInt(course[i].getDay()) - 1][j - 1] += "//" + course[i].getName() + ":" + course[i].getContext();
                } else if (courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] == null && j != Integer.parseInt(course[i].getBepos()))
                    courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] = course[i].getName() + "-课时" + (j - Integer.parseInt(course[i].getBepos()) + 1);
                else if (courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] != null && j != Integer.parseInt(course[i].getBepos()))
                    courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] += "//" + course[i].getName() + "-课时" + (j - Integer.parseInt(course[i].getBepos()) + 1);

        }
        data.put("courses", courses);
        data.put("contexts", contexts);
        return data;
    }
    @Override
    public Course[]selectCourseByName(String name)
    {
        return courseMapper.selectCourseByName(name);
    }
    @Override
    public Course selectCourseByCourseName(String name)
    {return courseMapper.selectCourseByCourseName(name);}
    @Override
    public int modifyContext(Course course)
    {return courseMapper.modifyContext(course);}

    @Override
    public Map<String, String[][]> selectDataByCourse(List<Course[]> listCourse,boolean flag) {
        int size=0;
        for(int i=0;i<listCourse.size();i++)
        {
            if(listCourse.get(i).length>0)
            {
                size+=listCourse.get(i).length;
            }
        }
        String courses[][] = new String[7][13];
        String contexts[][] = new String[7][13];
        String optionList[][]=new String[3][size];
        int optionindex=0;

        Map<String, String[][]> data = new HashMap<>();
        for (int m = 0; m < listCourse.size(); m++) {
            Course[] course = listCourse.get(m);
            if (m == 0&&!flag) {
                for (int i = 0; i < course.length; i++) {
                    for (int j = Integer.parseInt(course[i].getBepos()); j <= Integer.parseInt(course[i].getEnpos()); j++) {
                        if (courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] == null && j == Integer.parseInt(course[i].getBepos())) {
                            courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] = course[i].getName() +
                                    "\t任课:" + course[i].getTeacher() +
                                    "\t时间:第" + course[i].getBegin() + "周-第" + course[i].getEnd() + "周\t课时1";
                            contexts[Integer.parseInt(course[i].getDay()) - 1][j - 1] = "退课";
                            optionList[0][optionindex]=Integer.toString(Integer.parseInt(course[i].getDay())-1);
                            optionList[1][optionindex]=Integer.toString(Integer.parseInt(course[i].getBepos())-1);
                            optionList[2][optionindex++]=course[i].getName();

                        } else if (courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] != null && j == Integer.parseInt(course[i].getBepos())) {
                            courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] += "//" + course[i].getName() +
                                    "\t任课:" + course[i].getTeacher() +
                                    "\t时间:第" + course[i].getBegin() + "周-第" + course[i].getEnd() + "周\t课时1";
                            optionList[0][optionindex]=Integer.toString(Integer.parseInt(course[i].getDay())-1);
                            optionList[1][optionindex]=Integer.toString(Integer.parseInt(course[i].getBepos())-1);
                            optionList[2][optionindex++]=course[i].getName();
                        } else if (courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] == null && j != Integer.parseInt(course[i].getBepos()))
                            courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] = course[i].getName() + "-课时" + (j - Integer.parseInt(course[i].getBepos()) + 1);
                        else if (courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] != null && j != Integer.parseInt(course[i].getBepos()))
                            courses[Integer.parseInt(course[i].getDay()) - 1][j - 1] += "//" + course[i].getName() + "-课时" + (j - Integer.parseInt(course[i].getBepos()) + 1);
                    }

                }
            } else {
                for (int p = 0; p < course.length; p++){
                        int k = Integer.parseInt(course[p].getBepos());
//                    for (int k = Integer.parseInt(course[p].getBepos()); k <= Integer.parseInt(course[p].getEnpos()); k++) {
//                        if(contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1]!=null)
//                            if (contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1].equals("退课")) {
//                                    continue;
//                            }
                        if (contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1] == null && k == Integer.parseInt(course[p].getBepos())) {
                            contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1] = course[p].getName() +
                                    "\t任课:" + course[p].getTeacher() +
                                    "\t时间:第" + course[p].getBegin() + "周-第" + course[p].getEnd() + "周\t第"+course[p].getBepos()+"节-第"+course[p].getEnpos()+"节";
                            optionList[0][optionindex]=Integer.toString(Integer.parseInt(course[p].getDay())-1);
                            optionList[1][optionindex]=Integer.toString(Integer.parseInt(course[p].getBepos())-1);
                            optionList[2][optionindex++]=course[p].getName();
                        } else if (contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1] != null && k == Integer.parseInt(course[p].getBepos())) {
                            contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1] += "//" + course[p].getName() +
                                    "\t任课:" + course[p].getTeacher() +
                                    "\t时间:第" + course[p].getBegin() + "周-第" + course[p].getEnd() + "周\t第"+course[p].getBepos()+"节-第"+course[p].getEnpos()+"节";
                            optionList[0][optionindex]=Integer.toString(Integer.parseInt(course[p].getDay())-1);
                            optionList[1][optionindex]=Integer.toString(Integer.parseInt(course[p].getBepos())-1);
                            optionList[2][optionindex++]=course[p].getName();
                        }
//                         else if (contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1] == null && k != Integer.parseInt(course[p].getBepos()))
//                            contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1] = course[p].getName() + "-课时" + (k - Integer.parseInt(course[p].getBepos()) + 1);
//                        else if (contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1] != null && k != Integer.parseInt(course[p].getBepos()))
//                            contexts[Integer.parseInt(course[p].getDay()) - 1][k - 1] += "//" + course[p].getName() + "-课时" + (k - Integer.parseInt(course[p].getBepos()) + 1);
                    }
            }

        }
        data.put("optionList",optionList);
        data.put("havecourse", courses);
        data.put("haventcourse", contexts);
        return data;
    }
}