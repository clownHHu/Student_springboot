package com.example.demo.service.impl;


import com.example.demo.mapper.LoginMapper;
import com.example.demo.model.Login;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hxc
 * @dateTime: 2021-12-2
 * @description: 登录service
 * */
@Service
public class LoginServiceImpl implements LoginService {
    /**
     * 注入mapper到service层
     */
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Login selectLoginById(String id) {
        return loginMapper.selectLoginById(id);
    }
    @Override
    public int modifyPasswdById(String id,String password){
        return loginMapper.modifyPasswdById(id,password);
    }
}

