package com.example.demo.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Achieve {
    private String achid;
    private String id;
    private String name;
    private String time;
    private String context;
    private int state;
    private String path;

    public Achieve(String id, String time, String context, int state, String path)
    {
        this.id=id;
        this.time=time;
        this.context=context;
        this.state=state;
        this.path=path;
    }

}
