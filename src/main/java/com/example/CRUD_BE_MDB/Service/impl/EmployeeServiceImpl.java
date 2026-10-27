package com.example.CRUD_BE_MDB.Service.impl;

import com.example.CRUD_BE_MDB.Model.Employee;
import com.example.CRUD_BE_MDB.Repository.EmployeeRepository;
import com.example.CRUD_BE_MDB.Service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null); // Return null or throw an exception if not found
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(String id, Employee employee) {
        if (employeeRepository.existsById(id)) {
            employee.setId(id);
            return employeeRepository.save(employee);
        }
        return null; // Return null or throw an exception if not found
    }

    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
}