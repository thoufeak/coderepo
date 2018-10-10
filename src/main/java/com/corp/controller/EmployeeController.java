package com.corp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.corp.config.AppConfigurator;
import com.corp.exception.AppException;
import com.corp.model.Employee;
import com.corp.model.Message;
import com.corp.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	Logger logger = Logger.getLogger(EmployeeController.class.getName());

	@Autowired
	AppConfigurator appConfig;

	@Autowired
	EmployeeService empService;

	@PostConstruct
	public void createFile() {
		logger.log(Level.INFO, "Checking Directories and Files");
		File directory = new File(appConfig.getFolderPath());
		File f = new File(directory, appConfig.getFileName());
		try {
			if (!directory.isDirectory()) {
				logger.log(Level.INFO, " Directory Not Exists, creating");
				directory.mkdirs();
			}
			if (!f.exists()) {
				logger.log(Level.INFO, " File Not Exists, creating new file");
				f.createNewFile();
			}

		} catch (IOException ie) {
			logger.log(Level.WARNING, " Diectory / File creation error ", ie);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		logger.log(Level.INFO, "Get All Employees");
		List<Employee> empList = empService.readEmployee();
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
	}

	@RequestMapping(path = "/filterByAge", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> filterEmployee(@RequestParam String operator, @RequestParam String value,
			@RequestParam String sort) {

		logger.log(Level.INFO, "Filter Employee - I/P Params - Operator {0}, value {1}, sort {2}", new Object[]{operator, value, sort});

		List<Employee> empList = empService.readEmployee();

		List<Employee> filterList = null;

		try {
			int intVal = new Integer(value);
			filterList = empService.filterEmployee(empList, operator, intVal);
		} catch (NumberFormatException ne) {
			return new ResponseEntity<List<Employee>>(new ArrayList<Employee>(), HttpStatus.BAD_REQUEST);
		}
		filterList = empService.sortEmployee(filterList, sort);
		return new ResponseEntity<List<Employee>>(filterList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Message> addEmployee(@RequestBody Employee emp) {
		Message msg = new Message();
		List<Employee> empList = empService.readEmployee();
		try {
			if (empList != null) {
				for (Employee tmpEmp : empList) {
					if (emp.getId() == tmpEmp.getId()) {
						throw new AppException("Employee Id already Exists");
					}
				}
				empList.add(emp);
			} else {
				empList = new ArrayList<Employee>();
				empList.add(emp);
			}
			boolean status = empService.writeEmployee(empList);
			if (!status) {
				throw new AppException("Error in writing data, try again");
			}
		} catch (AppException ex) {
			msg.setInfo(ex.getMessage());
			return new ResponseEntity<Message>(msg, HttpStatus.CONFLICT);
		}
		msg.setInfo("Employee Added Successfully");
		return new ResponseEntity<Message>(msg, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Message> updateEmployee(@RequestBody Employee emp) {
		Message msg = new Message();
		boolean recordFound = false;
		List<Employee> empList = empService.readEmployee();
		try {
			if (empList != null) {
				for (Employee tmpEmp : empList) {
					if (emp.getId() == tmpEmp.getId()) {
						recordFound = true;
						tmpEmp.setFullName(emp.getFullName());
						tmpEmp.setAge(emp.getAge());
						tmpEmp.setSalary(emp.getSalary());
						break;
					}
				}
				if (recordFound) {
					boolean status = empService.writeEmployee(empList);
					if (!status) {
						throw new AppException("Error in writing data, try again");
					}
				} else {
					throw new AppException("No Record Found");
				}
			}
		} catch (AppException ex) {
			msg.setInfo(ex.getMessage());
			return new ResponseEntity<Message>(msg, HttpStatus.CONFLICT);
		}
		msg.setInfo("Employee Updated Successfully");
		return new ResponseEntity<Message>(msg, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Employee> removeEmployee(@RequestBody Employee emp) {
		Message msg = new Message();
		boolean recordFound = false;
		List<Employee> empList = empService.readEmployee();
		int index = 0;
		Employee remEmp = new Employee();
		try {
			if (empList != null) {
				for (Employee tmpEmp : empList) {
					if (emp.getId() == tmpEmp.getId()) {
						System.out.println("Match Found");
						recordFound = true;
						break;
					}
					index++;
				}
				if (recordFound) {
					remEmp = empList.get(index);
					empList.remove(index);
					boolean status = empService.writeEmployee(empList);
					if (!status) {
						throw new AppException("Error in writing data, try again");
					}
				} else {
					throw new AppException("No Record Found");
				}
			}
		} catch (AppException ex) {
			return new ResponseEntity<Employee>(remEmp, HttpStatus.CONFLICT);
		}
		msg.setInfo("Employee Updated Successfully");
		return new ResponseEntity<Employee>(remEmp, HttpStatus.OK);
	}

}
