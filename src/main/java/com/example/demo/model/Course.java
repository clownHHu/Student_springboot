package com.example.demo.model;

import lombok.Data;

@Data
public class Course {
    private String term;
    private String name;
    private String teacher;
    private String context;
    private String credit;//学分
    private String begin;//开课周数
    private String end;//结束周数
    private String bepos;//课表开课位置
    private String enpos;//课表结束位置
    private String day;//周几
    private String type;
    private int num;


}
