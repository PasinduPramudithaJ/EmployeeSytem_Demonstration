package com.example.CRUD_BE_MDB.Service;

import com.example.CRUD_BE_MDB.DTO.EmployeeDTO;
import com.example.CRUD_BE_MDB.Model.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(String id);
    List<Employee> getAllEmployees();
    EmployeeDTO updateEmployee(String id, EmployeeDTO employeeDTO);
    void deleteEmployee(String id);
    // Transactional multi-employee operational method
    List<EmployeeDTO> bulkCreateEmployees(List<EmployeeDTO> employeeDTOs);

    EmployeeDTO transferDepartmentAndAdjustSalary(String id, String newDepartment, Double salaryIncrement);

}
