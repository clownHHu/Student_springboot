package com.example.demo.service.impl;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.Achieve;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    /**
     * 注入mapper到service层
     */
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student selectStudentById(String id) {
        return studentMapper.selectStudentById(id);
    }
    @Override
    public int modifyStudent(String id,String name,String sex,String college,String classn,String phone,
                             String major,String idnumber,String date,
                             String nativeplace,String polstatus,String location,String remark,String avatar) {
        return studentMapper.modifyStudent(id,name,sex,college,classn,phone,major,idnumber,date,nativeplace,polstatus,location,remark,avatar);
    }
    @Override
    public int modifyAvatarById(String path,String id)
    {
        return studentMapper.modifyAvatarById(path,id);
    }
    @Override
    public Student bulidStudentByRequest(HttpServletRequest request) {
        Student student=new Student();
        student.setId(request.getParameter("id"));
        student.setName(request.getParameter("name"));
        student.setSex(request.getParameter("sex"));
        student.setCollege(request.getParameter("college"));
        student.setClassn(request.getParameter("classn"));
        student.setPhone(request.getParameter("phone"));
        student.setMajor(request.getParameter("major"));
        student.setIdnumber(request.getParameter("idnumber"));
        student.setDate(request.getParameter("date"));
        student.setNativeplace(request.getParameter("nativeplace"));
        student.setPolstatus(request.getParameter("polstatus"));
        student.setLocation(request.getParameter("location"));
        student.setRemark(request.getParameter("remark"));
        return student;
    }
    @Override
    public String uploadAvatar(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String path=null;
        String type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
        if ("JPG".equals(type.toUpperCase())||"JPEG".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())) {
            //获取服务器名
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            // 自定义的文件名称
            String trueFileName = new Date().getTime()+"."+type;
            // 设置存放文件的路径

            path = "D:/upload/student/" + trueFileName;
            File dest = new File(path);
            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            file.transferTo(dest);
            //发送设置映射路径
            path = "image/student/" + trueFileName;
//                map.put("imgUrl",path);
            //studentService.modifyAvatarById(path,id);

        }
        return path;
    }
    @Override
    public boolean compareStu(Student stu1,Student stu2)
    {

        boolean flag=false;
        if(stu1.getAvatar().equals(stu2.getAvatar())&&stu1.getId().equals(stu2.getId())&&stu1.getClassn().equals(stu2.getClassn())
        &&stu1.getCollege().equals(stu2.getCollege())&&stu1.getName().equals(stu2.getName())&&stu1.getSex().equals(stu2.getSex())
        &&stu1.getPhone().equals(stu2.getPhone())&&stu1.getMajor().equals(stu2.getMajor())&&stu1.getIdnumber().equals(stu2.getIdnumber())
        &&stu1.getDate().equals(stu2.getDate())&&stu1.getNativeplace().equals(stu2.getNativeplace())&&stu1.getPolstatus().equals(stu2.getPolstatus())
        &&stu1.getLocation().equals(stu2.getLocation())&&stu1.getRemark().equals(stu2.getRemark()))
            flag=true;
        return flag;
    }
    public ArrayList<Student> selectStudentByCC(String college,String classn){
        return studentMapper.selectStudentByCC(college,classn);
    }
    public ArrayList<Student> selectStudentByCollege(String college){
        return studentMapper.selectStudentByCollege(college);
    }
    public String uploadAchieve(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String path=null;
        String type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
        // 自定义的文件名称
        String trueFileName = new Date().getTime()+"."+type;
        // 设置存放文件的路径
        path = "D:/upload/Achieve/" + trueFileName;
        File dest = new File(path);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        file.transferTo(dest);
        //发送设置映射路径
        path = "image/Achieve/" + trueFileName;
        return path;
    }
    public Achieve[] selectAchieveById(String id){
        return studentMapper.selectAchieveById(id);
    }
}
