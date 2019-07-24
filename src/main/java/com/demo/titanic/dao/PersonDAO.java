package com.demo.titanic.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.demo.titanic.dto.Person;

@Repository
public interface PersonDAO extends PagingAndSortingRepository<Person, Long> {
	/*
	 * 
	 * DataAccessObject for Person DTO.
	 * Extends Spring JAP's PagingAndSortingRepository to perform CRUD operations and Paging queries.
	 * CRUD methods work through reflection mechanism that identifies the DTO at runtime and applying the code, thus facilitating reusability.
	 * 
	 */
}
