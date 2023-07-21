package com.example.demo.model;


import lombok.Data;

@Data
public class Login {

        private String id;
        private String passwd;
        private int status;

        public Login(String id, String passwd, int status) {
                this.id = id;
                this.status = status;
                this.passwd = passwd;
        }
}

