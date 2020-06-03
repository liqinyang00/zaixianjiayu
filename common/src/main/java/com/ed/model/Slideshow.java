package com.ed.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Slideshow implements Serializable {

    private Integer id;

    private String  img;

    private String url;

    private Integer status;

}
