package com.example.demo.service.impl;

import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.ScoreMapper;
import com.example.demo.model.Course;
import com.example.demo.model.Score;
import com.example.demo.service.CourseService;
import com.example.demo.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ScoreServiceImpl implements ScoreService {
    /**
     * 注入mapper到service层
     */
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private CourseService courseService;

    @Override
    public Score[] selectScoreByTI(String id, String term) {
        return scoreMapper.selectScoreByTI(id,term);
    }
    @Override
    public Score selectScoreByNI(String id,String coursename)
    {
        return scoreMapper.selectScoreByNI(id,coursename);
    }
    @Override
    public int insertScore(Score score){
        return scoreMapper.insertScore(score);
    }
    @Override
    public int deleteScoreByNI(String id,String coursename)
    {
        return scoreMapper.deleteScoreByNI(id,coursename);
    }
    @Override
    public Score[] getAllScore(String id) {
        return scoreMapper.getAllScore(id);
    }
    @Override
    public Score[] selectScoreByName(String name){return scoreMapper.selectScoreByName(name);}
    @Override
    public int modifyScore(Score score){return scoreMapper.modifyScore(score);}
    @Override
    public Map<String, List<String>> getDataByScore(Score score[])
    {
        Map<String, List<String>>data = new HashMap<>();
        List<String> scores=new ArrayList<>();
        List<String> courses=new ArrayList<>();
        List<String> credits=new ArrayList<>();
        List<String> terms=new ArrayList<>();
        List<String> totals=new ArrayList<>();
        List<String> types=new ArrayList<>();
        for(int i=0;i<score.length;i++)
        {
            scores.add(score[i].getScore());
            Course course=courseService.selectCourseByTN(score[i].getCourseterm(),score[i].getCoursename());
            courses.add(course.getName());
            credits.add(course.getCredit());
            terms.add(course.getTerm());
            if(Integer.parseInt(score[i].getScore())>60)
                totals.add(course.getCredit());
            else totals.add("0");
            types.add(course.getType());
        }
        data.put("type",types);
        data.put("score",scores);
        data.put("course",courses);
        data.put("credit",credits);
        data.put("term",terms);
        data.put("total",totals);
        return data;
    }
}
