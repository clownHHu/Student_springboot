package com.example.demo.service;


import com.example.demo.model.Achieve;

import org.apache.ibatis.annotations.Param;



public interface AchieveService {
    Achieve[] getAllAchieve(String id);//获取全部科研成果的接口
    int insertAchieve(Achieve achieve);//插入科研成果的接口
}