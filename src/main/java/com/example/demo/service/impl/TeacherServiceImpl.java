package com.example.demo.service.impl;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.model.Achieve;
import com.example.demo.model.Relation;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

@Service
public class TeacherServiceImpl implements TeacherService {
    /**
     * 注入mapper到service层
     */
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher selectTeacherById(String id) {
        return teacherMapper.selectTeacherById(id);
    }
    @Override
    public int modifyAvatarById(String path,String id) {
        return teacherMapper.modifyAvatarById(path,id);
    }
    @Override
    public int modifyTeacher(Teacher teacher){
        return teacherMapper.modifyTeacher(teacher);
    }
    @Override public Relation[] selectRelationById(String id){
        return teacherMapper.selectRelationById(id);
    }
    @Override
    public Achieve selectAchieveByIT(String id, String time){
        return teacherMapper.selectAchieveByIT(id,time);
    }
    @Override
    public int insertAchieve(Achieve achieve){
        return teacherMapper.insertAchieve(achieve);
    }
    @Override
    public Teacher selectTeacherByName(String name){
        return teacherMapper.selectTeacherByName(name);
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

            path = "D:/upload/teacher/" + trueFileName;
            File dest = new File(path);
            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            file.transferTo(dest);
            //发送设置映射路径
            path = "image/teacher/" + trueFileName;
//                map.put("imgUrl",path);
            //studentService.modifyAvatarById(path,id);

        }
        return path;
    }
    @Override
    public Teacher bulidTeacherByRequest(HttpServletRequest request) {
        Teacher teacher=new Teacher();
        teacher.setId(request.getParameter("id"));
        teacher.setName(request.getParameter("name"));
        teacher.setSex(request.getParameter("sex"));
        teacher.setCollege(request.getParameter("college"));
        teacher.setMarriage(request.getParameter("marriage"));
        teacher.setPhone(request.getParameter("phone"));
        teacher.setDate(request.getParameter("date"));
        teacher.setNativeplace(request.getParameter("nativeplace"));
        teacher.setPolstatus(request.getParameter("polstatus"));
        teacher.setLocation(request.getParameter("location"));
        teacher.setTitle(request.getParameter("remark"));
        return teacher;
    }
    @Override
    public boolean compareTea(Teacher tea1,Teacher tea2)
    {
        boolean flag=false;
        if(tea1.getAvatar().equals(tea2.getAvatar())&&tea1.getId().equals(tea2.getId())&&tea1.getMarriage().equals(tea2.getMarriage())
                &&tea1.getCollege().equals(tea2.getCollege())&&tea1.getName().equals(tea2.getName())&&tea1.getSex().equals(tea2.getSex())
                &&tea1.getPhone().equals(tea2.getPhone()) &&tea1.getDate().equals(tea2.getDate())
                &&tea1.getNativeplace().equals(tea2.getNativeplace())&&tea1.getPolstatus().equals(tea2.getPolstatus())
                &&tea1.getLocation().equals(tea2.getLocation())&&tea1.getTitle().equals(tea2.getTitle()))
            flag=true;
        return flag;
    }
}
