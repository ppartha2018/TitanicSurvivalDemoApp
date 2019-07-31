package com.demo.titanic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.demo.titanic.dao.PersonDAO;
import com.demo.titanic.dao.PersonSpecification;
import com.demo.titanic.dto.Person;
import com.demo.titanic.utils.CsvUtils;

@Controller
@RequestMapping(path="/demo")
public class MainController {

	@Autowired
	private PersonDAO personDao;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	/*
	 * Service method to serve the upload CSV request. 
	 * The csv file from the multi-part form data in POST request is deserialized with Jacskon CSV mapper.
	 * The List<Person> dto's are then persisted with personDAO (spring JAP repository) saveAll method.
	 * personDao is initialized with Spring's Dependency Injection mechanism.
	 * 
	 */
	@PostMapping(value = "/upload")
    public @ResponseBody String uploadSimple(@RequestParam("file") MultipartFile file) {
		try {
			//persist all records to the database or perform an update if any record contains key which is already present in the database.
			//In this case, the passengerId (primary key) of the Person DTO entity is used to uniquely identify the record.
			personDao.saveAll(CsvUtils.read(Person.class, file.getInputStream()));
			return "Upload Successful!";
		} catch (IOException e) {
			//to-do Exception logs through a logging framework for efficiency
			//for now, this is reasonable
			e.printStackTrace();
			return "Error uploading the file.";
		}
    }
	
	
	/*
	 * Method to serve grid of records with sorting and filter functionality with Pagination.
	 * Pagination, Sorting is applied by using Spring JPA Repository's Page and Sort Interfaces. 
	 * PersonSpecfication constructs Expressions of Conditions based on filters applied (in queryparameters) and added as
	 * part of where condition in the resulting query (through  JPA/Hibernate's critera query API).
	 * 
	 * The Pageable collection add fields related to pagination page start and size which are used by UI client to display paged records in the grid.
	 * Again, Jackson Mapper takes care of serializing the paged records into appropriate json object that is returned as response eventually.
	 * The method returns Map of objects which contains paginated records for the grid, and data for the chart.
	 */
	@GetMapping(value = "/passengers", produces = "application/json")
	public @ResponseBody Map<Object, Object> getPassengers(@RequestParam("pagenum") int pageNum, @RequestParam("pagesize") int pageSize, @RequestParam("sortdatafield") Optional<String> sortField
			, @RequestParam("sortorder") Optional<String> sortOrder, @RequestParam("filterscount") Optional<Integer> filterCount, @RequestParam Map<String, String> queryParameters){
	
		Map<Object, Object> result = new HashMap<>();
		Pageable pageCondition = null;
		Page<Person> persons = null;
		
		try {
			if(sortField != null && sortField.isPresent() && !sortField.get().equals("")) {
				//default sort order is ASC if ordering is not specified or wrong values given for sort order
				Direction sortDirection = Sort.Direction.ASC;
				if(sortOrder != null && sortOrder.isPresent() && !sortOrder.get().equals("")) {
					sortDirection = (sortOrder.get().equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC);
				}
				pageCondition = PageRequest.of(pageNum, pageSize, Sort.by(sortDirection, sortField.get()));
			} else {
				pageCondition = PageRequest.of(pageNum, pageSize);
			}
			Specification<Person> specs = PersonSpecification.applyFilter(filterCount.get(), queryParameters, null, false);
			persons = personDao.findAll(specs, pageCondition);
			result.put("records", persons);
			
			//content for the chart - can be split to form a separate method
			//add groupBy conditions - can be improved
			specs = PersonSpecification.applyFilter(filterCount.get(), queryParameters, "survived", true);
			long survivedYes = personDao.count(specs);
			specs = PersonSpecification.applyFilter(filterCount.get(), queryParameters, "survived", false);
			long survivedNo = personDao.count(specs);
			System.out.println("REsult of count by survivedNo: "+ survivedNo);
			result.put("survivedYes", survivedYes);
			result.put("survivedNo", survivedNo);
			
		} catch(Exception e) {
			//can be extended to throw error code and custom error json
			// by default, jackson throws proper http status codes and error json for frequent error cases
			// error logging with a logging framework
			e.printStackTrace();
		}

		return result;
	}
	
	
	// rest method to get single user
	@GetMapping(value = "/passengers/{passenger_id}", produces = "application/json")
	public @ResponseBody Optional<Person> getPassenger(@PathVariable("passenger_id") Long passengerId){
		return personDao.findById(passengerId);
	}
	
	// rest method to create/update single record
	@PostMapping(value = "/passengers", consumes = "application/json", produces="application/json")
	public ResponseEntity<Person> create(@RequestBody Person person) {
		try {
			//can add more validation cases
			System.out.println(person.getName());
			return ResponseEntity.status(HttpStatus.OK).body(personDao.save(person));
		}
		catch(Exception e) {
			//Most of the major exceptions are handled by the framework
			//Error flow for save record
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}	
	
	// delete rest method with appropriate boolean response
	@DeleteMapping(value = "/passengers/{passengerId}")
	public ResponseEntity<Boolean> delete(@PathVariable("passengerId") Long passengerId) {
		try {
			//can add more validation cases
			personDao.deleteById(passengerId);
			return ResponseEntity.status(HttpStatus.OK).body(true);
		} catch(Exception e) {
			//Most of the major exceptions are handled by the framework
			//Error flow for save record
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
		
	}
}
