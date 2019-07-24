package com.demo.titanic.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.demo.titanic.utils.NumericBooleanDeserializer;
import com.demo.titanic.utils.NumericIntegerDeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
public class Person implements Serializable {

	/**
	 * DTO Object that reflects the database table.
	 * JPA/ORM framework methods work on transfering this entity to and from database. Jackson framework methods work on objects of this type
	 * for coversion between POJO's to CSV/JSON and vice versa.
	 * JsonProperty tags are used to as a customer mapper between properties on csv file to database field names.
	 * Some of the fields are augmented with Deserializer to help in right conversion of some data types from fields in csv.
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@JsonProperty("PassengerId")
	private Long passengerId;
	
	@JsonProperty("Survived")
	@JsonDeserialize(using=NumericBooleanDeserializer.class)
	private boolean survived;
	
	@JsonProperty("Pclass")
	private int passengerClass;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("Sex")
	private String sex;
	
	@JsonProperty("Age")
	@JsonDeserialize(using=NumericIntegerDeSerializer.class)
	private int age;
	
	@JsonProperty("SibSp")
	private int siblingSpouseCount;
	
	@JsonProperty("Parch")
	private int parentChildCount;
	
	@JsonProperty("Ticket")
	private String ticketId;
	
	@JsonProperty("Fare")
	private double fare;
	
	@JsonProperty("Cabin")
	private String cabinId;
	
	@JsonProperty("Embarked")
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
