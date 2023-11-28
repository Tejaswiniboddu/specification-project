package com.specification.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.specification.project.dto.PageRequestDTO;
import com.specification.project.dto.RequestDTO;
import com.specification.project.entity.Employee;
import com.specification.project.repository.EmployeeRepository;
import com.specification.project.service.SpecificationService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	@Autowired
	private SpecificationService<Employee> specificationService;
	
	@PostMapping("/name")
	public String saveEmployee(@RequestBody Employee employee) {
		 employeeRepository.save(employee);
		 return "Successfully saved";
	}
	
	@GetMapping("/emplolist")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
	}
	
	@GetMapping("/emp/{name}")
	public Employee getEmployeeByName(@PathVariable(name="name")String name) {
		return employeeRepository.findByName(name);
	}
	
	//Single input
//	@PostMapping("/specification")
//	public List<Employee> getEmployee(){
//	Specification<Employee> specification=new Specification<Employee>() {
//
//			@Override
//			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//				
//				return criteriaBuilder.equal(root.get("id"),"2");
//			}
//			
//		};
//		
//		List<Employee>all=employeeRepository.findAll(specification);
//		return all;
//	}
	
	//List of input using AND operator
//	@PostMapping("/specification")
//	public List<Employee> getEmployee(@RequestBody RequestDTO requestDTO){
//	Specification<Employee> searchSpecification= specificationService.getSearchSpecification(requestDTO.getSearchRequestDTO());
//	
//	return employeeRepository.findAll(searchSpecification);
//	}

//	list of inputs using OR Operator
	@PostMapping("/specification")
	public List<Employee> getEmployee(@RequestBody RequestDTO requestDTO){
	Specification<Employee> searchSpecification= specificationService.
			getSearchSpecification(requestDTO.getSearchRequestDTO(),requestDTO.getGlobalOperator());
	
	return employeeRepository.findAll(searchSpecification);
	
	}
	
	@PostMapping("/specification/pagination")
	public Page<Employee> getEmployeePage(@RequestBody RequestDTO requestDTO){
	Specification<Employee> searchSpecification= specificationService.
			getSearchSpecification(requestDTO.getSearchRequestDTO(),requestDTO.getGlobalOperator());
	Pageable pageable= new PageRequestDTO().getPageable(requestDTO.getPageDTO());
	
	
	return employeeRepository.findAll(searchSpecification,pageable);
	
	}
}
