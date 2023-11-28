package com.specification.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.specification.project.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>,JpaSpecificationExecutor<Company>{

	Company findById(long id);
	List<Company> findByEmployeeName(String employeeName);
	


}
