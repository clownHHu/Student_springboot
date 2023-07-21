package com.example.demo.service;


import com.example.demo.model.Login;

public interface LoginService {
    Login selectLoginById(String id);//查询登陆表
    int modifyPasswdById(String id,String password);//修改密码
}
