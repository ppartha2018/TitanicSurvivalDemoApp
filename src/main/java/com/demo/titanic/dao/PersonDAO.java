package com.demo.titanic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.demo.titanic.dto.Person;

@Repository
public interface PersonDAO extends PagingAndSortingRepository<Person, Long>, JpaSpecificationExecutor<Person> {
	/*
	 * 
	 * DataAccessObject for Person DTO.
	 * Extends Spring JAP's PagingAndSortingRepository to perform CRUD operations and Paging queries.
	 * CRUD methods work through reflection mechanism that identifies the DTO at runtime and applying the code, thus facilitating reusability.
	 * 
	 */
	
	//Additional methods to support filtering functionalities
	// this utilizes Spring JPA's automatic query creation mechanism through field names embedded in method names
	
	// will generate the query: select * from Person where sex=@sex AND age=@age;
	//just mentioned here for an example
	List<Person> findBySexAndAge(String sex, int age);
}
