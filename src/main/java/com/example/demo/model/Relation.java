package com.example.demo.model;

import lombok.Data;

@Data
public class Relation {
    String stuid;
    String teaid;
    String admid;

    public Relation(String stuid, String teaid, String admid) {
        this.stuid = stuid;
        this.teaid = teaid;
        this.admid = admid;
    }
}
