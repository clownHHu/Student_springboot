package com.example.demo.service.impl;

import com.example.demo.mapper.AchieveMapper;

import com.example.demo.model.Achieve;

import com.example.demo.service.AchieveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class AchieveServiceImpl implements AchieveService {
    /**
     * 注入mapper到service层
     */
    @Autowired
    private AchieveMapper achieveMapper;

    @Override
    public Achieve[] getAllAchieve(String id){return achieveMapper.getAllAchieve(id);}
    @Override
    public int insertAchieve(Achieve achieve){return achieveMapper.insertAchieve(achieve);}


}
