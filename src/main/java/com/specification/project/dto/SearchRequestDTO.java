package com.specification.project.dto;

import lombok.Data;

@Data
public class SearchRequestDTO {
	
	String column;
	String value;
	Operator operator;
	String joinTable;
	
	
	public enum Operator{
		EQUAL,LIKE,IN,GREATER_THAN,LESS_THAN,BETWEEN,JOIN;
	}

}
