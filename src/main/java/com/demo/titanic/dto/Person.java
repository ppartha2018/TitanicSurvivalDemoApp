package com.demo.titanic.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.demo.titanic.utils.NumericBooleanDeserializer;
import com.demo.titanic.utils.NumericIntegerDeSerializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
public class Person implements Serializable {

	/**
	 * DTO Object that reflects the database table.
	 * JPA/ORM framework methods work on transferring this entity to and from database. Jackson framework methods work on objects of this type
	 * for conversion between POJO's properties to CSV/JSON properties and vice versa.
	 * JsonAlias tags are used as custom mappers between properties on certain upload (eg: csv) file to database field names.
	 * Some of the fields are augmented with De-serializer to help in right conversion of some data types from fields of different file format like csv.
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@JsonAlias("PassengerId")
	private Long passengerId;
	
	@JsonAlias("Survived")
	@JsonDeserialize(using=NumericBooleanDeserializer.class)
	private boolean survived;
	
	@JsonAlias("Pclass")
	private int passengerClass;
	
	@JsonAlias("Name")
	private String name;
	
	@JsonAlias("Sex")
	private String sex;
	
	@JsonAlias("Age")
	@JsonDeserialize(using=NumericIntegerDeSerializer.class)
	private int age;
	
	@JsonAlias("SibSp")
	private int siblingSpouseCount;
	
	@JsonAlias("Parch")
	private int parentChildCount;
	
	@JsonAlias("Ticket")
	private String ticketId;
	
	@JsonAlias("Fare")
	private double fare;
	
	@JsonAlias("Cabin")
	private String cabinId;
	
	@JsonAlias("Embarked")
	private String embarkedStation;
	
	public Long getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}
	public boolean isSurvived() {
		return survived;
	}
	
	public void setSurvived(boolean survived) {
		this.survived = survived;
	}
	
	public int getPassengerClass() {
		return passengerClass;
	}
	public void setPassengerClass(int passengerClass) {
		this.passengerClass = passengerClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSiblingSpouseCount() {
		return siblingSpouseCount;
	}
	public void setSiblingSpouseCount(int siblingSpouseCount) {
		this.siblingSpouseCount = siblingSpouseCount;
	}
	public int getParentChildCount() {
		return parentChildCount;
	}
	public void setParentChildCount(int parentChildCount) {
		this.parentChildCount = parentChildCount;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public String getCabinId() {
		return cabinId;
	}
	public void setCabinId(String cabinId) {
		this.cabinId = cabinId;
	}
	public String getEmbarkedStation() {
		return embarkedStation;
	}
	public void setEmbarkedStation(String embarkedStation) {
		this.embarkedStation = embarkedStation;
	}
	
	
}
