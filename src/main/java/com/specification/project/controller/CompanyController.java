package com.specification.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.specification.project.entity.Company;
import com.specification.project.repository.CompanyRepository;

@RestController
public class CompanyController {

	@Autowired
	public CompanyRepository companyRepository;
	
	@PostMapping("/company")
	public String saveCompany(@RequestBody Company company) {
		 companyRepository.save(company);
		 return "Successfully saved";
	}
	@GetMapping("/Company/{id}")
	public Company fetchCompanyById(@PathVariable("id") long id) {
	    return companyRepository.findById(id);
	}
	
	@GetMapping("/list/{employee}")
	public List<Company> findByEmployee(@PathVariable String employee){
		return companyRepository.findByEmployeeName(employee);
	}

	
	

}
