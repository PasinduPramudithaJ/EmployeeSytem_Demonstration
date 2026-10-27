package com.example.CRUD_BE_MDB.Service;

import com.example.CRUD_BE_MDB.Model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Employee getEmployeeById(String id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(String id, Employee employee);
    void deleteEmployee(String id);
}
