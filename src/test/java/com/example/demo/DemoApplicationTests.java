package com.example.demo;

import com.example.demo.mapper.AdminMapper;
import com.example.demo.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private AdminMapper adminMapper;
    @Test
    public void insertStudent(){
        Student student=new Student();
        student.setId("11");
        student.setName("测试");
        student.setCollege("信息工程学院");
        int num = adminMapper.insertStudent(student);
        System.out.println(num);

    }


}
