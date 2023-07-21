package com.example.demo.model;

import lombok.Data;

@Data
public class ScoreByCourse {
    private String id;
    private String name;
    private String score;
    public ScoreByCourse(String id,String name,String score)
    {
        this.id=id;
        this.name=name;
        this.score=score;
    }
}
