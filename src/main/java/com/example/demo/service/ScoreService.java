package com.example.demo.service;


import com.example.demo.model.Score;

import java.util.List;
import java.util.Map;

public interface ScoreService {
    Score selectScoreByNI(String id,String coursename);//根据学号课程名查找分数
    Score[] selectScoreByTI(String id, String term);//根据学号学期查找分数列表
    Score[] getAllScore(String id);//根据学号查找全部分数
    Map<String, List<String>> getDataByScore(Score score[]);//根据分数列表查看课程数据
    int insertScore(Score score);//插入分数
    int deleteScoreByNI(String id,String coursename);//删除分数
    Score[] selectScoreByName(String name);//根据名称查询分数列表
    int modifyScore(Score score);//修改分数
}