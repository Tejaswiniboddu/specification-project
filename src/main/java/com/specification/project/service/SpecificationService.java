package com.specification.project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.specification.project.dto.RequestDTO;
import com.specification.project.dto.RequestDTO.GlobalOperator;
import com.specification.project.dto.SearchRequestDTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class SpecificationService<T> {
	
	
//	sinlge input
	public Specification<T>getSearchSpecification(SearchRequestDTO searchRequestDTO){
		
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			
				return criteriaBuilder.equal(root.get(searchRequestDTO.getColumn()),searchRequestDTO.getValue());
			}
		};
	}
	
	
	//List of input using AND,OR operator
//	public Specification<T>getSearchSpecification(List<SearchRequestDTO> searchRequestDTOs,RequestDTO.GlobalOperator globalOperator){
//	
//		return (root, query, criteriaBuilder) -> {
//			
//			List<Predicate> predicate=new ArrayList<>();
//			
//			for(SearchRequestDTO requstDTO:searchRequestDTOs) {
//				Predicate equal =criteriaBuilder.equal(root.get(requstDTO.getColumn()),requstDTO.getValue());
//				predicate.add(equal);
//			}
//			if(GlobalOperator.equals(RequestDTO.GlobalOperator.AND)) {
//				return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
//			}
//			return criteriaBuilder.or(predicate.toArray(new Predicate[0]));
//		};
//
//		}

	
	//Equal,like and in operator
	
	public Specification<T>getSearchSpecification(List<SearchRequestDTO>searchRequestDTOs,RequestDTO.GlobalOperator globalOperator){
		return(root,query,criteriaBuilder)->{
			List<Predicate>predicates=new ArrayList<>();
			for(SearchRequestDTO requestDTO:searchRequestDTOs) {
				switch(requestDTO.getOperator()) {
				
				case EQUAL:
					Predicate equal=criteriaBuilder.equal(root.get(requestDTO.getColumn()), requestDTO.getValue());
					predicates.add(equal);
					break;
					
				case LIKE:
					Predicate like = criteriaBuilder.like(criteriaBuilder.lower(root.get(requestDTO.getColumn())), requestDTO.getValue().toString().toLowerCase() + "%");			
					predicates.add(like);
					break;
					
				case IN:
					String[] split=requestDTO.getValue().split(",");
					Predicate in=root.get(requestDTO.getColumn()).in(Arrays.asList(split));
					predicates.add(in);
					break;
					
				case LESS_THAN:	
					Predicate lessThan = criteriaBuilder.lessThan(root.get(requestDTO.getColumn()),requestDTO.getValue());
					predicates.add(lessThan);
					break;
					
				case GREATER_THAN:	
					Predicate greaterThan=criteriaBuilder.greaterThan(root.get(requestDTO.getColumn()), requestDTO.getValue());
					predicates.add(greaterThan);
					break;
				case BETWEEN:
					String[] split1	=requestDTO.getValue().split(",");
					Predicate between= criteriaBuilder.between(root.get(requestDTO.getColumn()),Long.parseLong(split1[0]),Long.parseLong(split1[1]));
					predicates.add(between);
					break;
					
				case JOIN:
					Predicate join= criteriaBuilder.equal(root.get(requestDTO.getJoinTable()).get(requestDTO.getColumn()),requestDTO.getValue());
					predicates.add(join);
					break;

				default:
					throw new IllegalStateException("Unexpected value:"+"");

				}
			}
			
            	if(globalOperator.equals(RequestDTO.GlobalOperator.AND)) {
            		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            	}
            	else {
            		return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            	}

			
		};
	}
	}
	

