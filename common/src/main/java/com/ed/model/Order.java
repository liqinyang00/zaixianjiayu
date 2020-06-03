package com.ed.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order  implements Serializable {

    private Integer orderid;

    private String  ordernumber;

    private String orderdate;

    private String ordertitle;

    private Double orderprice;
}
