package com.ed.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseEntity implements Serializable {

    private Integer courseid;

    private String coursetitle;

    private Integer coursetype;

    private Integer coursenumber;

    private String courseimg;

    private String authername;

    private String autherimg;

    private Double courseprice;

    private String coursecontext;

    private String coursetime;

    private String name;

    private String sectionname;

    private String video;

    private String duration;

}
