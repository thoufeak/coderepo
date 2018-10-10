package com.corp.model;

public class Employee {

	private Long id;
	
	private String fullName;
	
	private Integer age;
	
	private Double salary;
	
	public Employee(Long id, String fullName, Integer age, Double salary){
		this.id = id;
		this.fullName = fullName;
		this.age = age;
		this.salary = salary;
	}
	
	public Employee(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", fullName=" + fullName + ", age=" + age + ", salary=" + salary + "]";
	}
	
	
	
}
