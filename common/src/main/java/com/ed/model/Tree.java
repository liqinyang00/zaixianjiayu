package com.ed.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Tree implements Serializable {
	
	private Integer id;
	
	private String text;
	
	private String href;
	
	private Integer pid;
	
	private List<Tree> nodes;
	
	private Boolean selectable;
	
	private Boolean leaf;

}
