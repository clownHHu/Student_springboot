package com.example.demo.mapper;


import com.example.demo.model.Achieve;
import com.example.demo.model.Score;
import org.apache.ibatis.annotations.Param;

public interface AchieveMapper {

    Achieve[] getAllAchieve(String id);//通过学号查找数据库全部科研信息
    int insertAchieve(@Param("achieve") Achieve achieve);//插入科研信息

}
