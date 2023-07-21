package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.tool.CommonResult;

import com.example.demo.service.impl.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.example.demo.tool.MD5Util.Decrypt;


/**
 * @author hxc
 * @dateTime: 2021-12-2
 * @description: 登录Controller
 * */
@RestController
@Slf4j
@RequestMapping("/users")
public class LoginController {
    /**
     * 将StudentService注入controller层
     */
    @Autowired
    private LoginService loginService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AdminService adminService;
    //private static final String KEY = "abcdefgabcdefg12";

    // 登录到系统
    @PostMapping("/login")
    public CommonResult login(@RequestBody User user) {
        //String passwd=Decrypt(user.getPassword(),KEY );
        Login login=loginService.selectLoginById(user.getId());
        boolean flag=false;

        if(login!=null)
            if(login.getPasswd().equals(user.getPassword()))
                 flag=true;
        if (flag)
            switch (login.getStatus())
            {
                case 1:
                    Student student=studentService.selectStudentById(user.getId());
                    return new CommonResult(200,"1",student);
                case 2:
                    Teacher teacher=teacherService.selectTeacherById(user.getId());
                    return new CommonResult(200,"2",teacher);
                case 3:
                    Admin admin=adminService.selectAdminById(user.getId());
                    return new CommonResult(200,"3",admin);
                default:
                    return CommonResult.validateFailed();
            }
        else
            return CommonResult.validateFailed();
    }

    //验证密码
    @PostMapping("/checkPasswd")
    public CommonResult checkPasswd(@RequestParam String id,@RequestParam String password){
        Login login = loginService.selectLoginById(id);
        if(login!=null){
            return login.getPasswd().equals(password)?
                    new CommonResult(200,"good",null):new CommonResult(200,"bad",null);
        }
        else
            return new CommonResult(200,"no",null);
    }

    //修改密码
    @PostMapping("/changePasswd")
    public CommonResult changePasswd(@RequestParam String id,@RequestParam String password){
        int num = loginService.modifyPasswdById(id, password);
        if(num>0)
            return new CommonResult(200,"good",null);
        return CommonResult.validateFailed();
    }

}

