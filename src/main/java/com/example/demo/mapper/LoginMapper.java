package com.example.demo.mapper;

import com.example.demo.model.Login;

/**
 * @author hxc
 * @dateTime: 2021-12-2
 * @description: 登录mapper
 * */
public interface LoginMapper {
    Login selectLoginById(String id);
    int modifyPasswdById(String id,String password);
}
