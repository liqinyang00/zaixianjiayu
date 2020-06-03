package com.ed.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShoppingEntity implements Serializable {

    private Integer shoppingid;

    private String  shoppingcoursetitle;

    private Integer shoppingcourseid;

    private String shoppingcourseimg;

    private Double shoppingcourseprice;



}
