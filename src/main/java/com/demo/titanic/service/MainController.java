package com.demo.titanic.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.demo.titanic.dao.PersonDAO;
import com.demo.titanic.dto.Person;
import com.demo.titanic.utils.CsvUtils;

@Controller
@RequestMapping(path="/demo")
public class MainController {

	@Autowired
	private PersonDAO personDao;
	
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
	
	
	@GetMapping(value = "/passengers", produces = "application/json")
	public @ResponseBody Page<Person> getPassengers(@RequestParam("pagenum") int pageNum, @RequestParam("pagesize") int pageSize, @RequestParam("sortdatafield") Optional<String> sortField
			, @RequestParam("sortorder") Optional<String> sortOrder){
	
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
		
			persons = personDao.findAll(pageCondition);
		} catch(Exception e) {
			//can be extended to throw error code and custom error json
			// by default, jackson throws proper http status codes and error json for frequent error cases
			// error logging with a logging framework
			e.printStackTrace();
		}
		
		return persons;
	}
}
