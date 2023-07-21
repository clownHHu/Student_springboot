package com.example.demo.mapper;


import com.example.demo.model.Score;
import org.apache.ibatis.annotations.Param;

public interface ScoreMapper {

    Score[] selectScoreByTI(String id, String term);
    Score[] getAllScore(String id);
    Score selectScoreByNI(String id,String coursename);
    int insertScore(@Param(value="score")Score score);
    int deleteScoreByNI(String id,String coursename);
    Score[] selectScoreByName(String name);
    int modifyScore(@Param(value = "score") Score score);
}
