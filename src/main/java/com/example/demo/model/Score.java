package com.example.demo.model;

import lombok.Data;

@Data
public class Score {
    private String studentid;
    private String courseterm;
    private String score;
    private String coursename;
    public Score(String id,String term,String score,String coursename)
    {
        this.studentid=id;
        this.courseterm=term;
        this.score=score;
        this.coursename=coursename;
    }

}
