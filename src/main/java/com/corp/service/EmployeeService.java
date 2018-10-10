package com.corp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corp.config.AppConfigurator;
import com.corp.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EmployeeService {

	public static final String LESS_THAN = "lt";
	public static final String GREATER_THAN = "gt";
	public static final String LESS_THAN_EQUAL = "lte";
	public static final String GREATER_THAN_EQUAL = "gte";
	public static final String EQUAL = "eq";
	public static final String NOT_EQUAL = "ne";

	@Autowired
	AppConfigurator appConfig;

	public List<Employee> readEmployee() {
		String fileLocation = appConfig.getFolderPath() + appConfig.getFileName();
		List<Employee> empList = null;
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get(fileLocation));
			ObjectMapper objectMapper = new ObjectMapper();
			empList = objectMapper.readValue(jsonData, new TypeReference<List<Employee>>() {
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return empList;

	}

	public boolean writeEmployee(final List<Employee> empList) {
		String fileLocation = appConfig.getFolderPath() + appConfig.getFileName();
		boolean status = false;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File(fileLocation), empList);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public List<Employee> filterEmployee(final List<Employee> empList, final String operator, final int value) {
		Predicate<Employee> byAge = null;
		switch (operator) {
		case LESS_THAN:
			byAge = employee -> employee.getAge() < value;
			break;
		case LESS_THAN_EQUAL:
			byAge = employee -> employee.getAge() <= value;
			break;
		case GREATER_THAN:
			byAge = employee -> employee.getAge() > value;
			break;
		case GREATER_THAN_EQUAL:
			byAge = employee -> employee.getAge() >= value;
			break;
		case EQUAL:
			byAge = employee -> employee.getAge() == value;
			break;
		case NOT_EQUAL:
			byAge = employee -> employee.getAge() != value;
			break;
		default:
			byAge = employee -> employee.getAge() == value && employee.getAge() != value;
			break;
		}
		List<Employee> filteredList = empList.stream().filter(byAge).collect(Collectors.<Employee>toList());
		return filteredList;
	}

	public List<Employee> sortEmployee(final List<Employee> empList, final String sortType) {
		Collections.sort(empList, new SortByAge(sortType));
		return empList;
	}

	class SortByAge implements Comparator<Employee> {

		private String sortType;

		public SortByAge(String sortType) {
			this.sortType = sortType;
		}

		public int compare(Employee emp1, Employee emp2) {
			if ("asc".equals(this.sortType)) {
				return emp1.getAge() - emp2.getAge();
			}
			return emp2.getAge() - emp1.getAge();
		}
	}
}
