package com.ed.model;

import lombok.Data;

import java.io.Serializable;

@Data

public class UserEntity implements Serializable {

    private Integer userid;

    private String username;

    private String userpassword;

    private String userimage;

}