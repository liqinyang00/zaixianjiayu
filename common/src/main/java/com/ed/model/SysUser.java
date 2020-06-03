package com.ed.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUser implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String usercode;

    private String salt;

    private String locked;

}
