package com.specification.project.dto;

import java.util.List;

import lombok.Data;

@Data
public class RequestDTO {
	
	//request for one input 
	//private SearchRequestDTO searchRequestDTO;
	
	
	//list of the inputs
	
	private List<SearchRequestDTO> searchRequestDTO;
	
	private GlobalOperator globalOperator;
	
	
	
	private PageRequestDTO pageDTO;

	
	
	
	public enum GlobalOperator{
		AND,OR;
	}
}
