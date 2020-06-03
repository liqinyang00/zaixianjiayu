package com.ed.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Classify implements Serializable {

    private  Integer  id;

    private  String  name;

    private  Integer pid;

    private  Integer status;

    private  String  url;

}
