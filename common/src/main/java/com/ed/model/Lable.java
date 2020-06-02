package com.ed.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Lable implements Serializable {

    private Integer id;

    private Double  price;

    private Double  preferential;

    private Integer status;


}
