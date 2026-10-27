package com.example.CRUD_BE_MDB.Service.impl;

import com.example.CRUD_BE_MDB.DTO.EmployeeDTO;
import com.example.CRUD_BE_MDB.Model.Employee;
import com.example.CRUD_BE_MDB.Repository.EmployeeRepository;
import com.example.CRUD_BE_MDB.Service.EmployeeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee saved = employeeRepository.save(employee);
        return convertToDTO(saved);
    }

    @Override
    public EmployeeDTO getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(String id, EmployeeDTO employeeDTO) {
        if (!employeeRepository.existsById(id)) {
            return null;
        }
        Employee employee = convertToEntity(employeeDTO);
        employee.setId(id);
        Employee updated = employeeRepository.save(employee);
        return convertToDTO(updated);
    }

    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    // Example Transaction 1: Bulk creation with multi-document transaction support
    @Override
    @Transactional
    public List<EmployeeDTO> bulkCreateEmployees(List<EmployeeDTO> employeeDTOs) {
        List<Employee> employees = employeeDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        List<Employee> savedEmployees = employeeRepository.saveAll(employees);

        return savedEmployees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Example Transaction 2: Department transfer and salary modification atomic transaction
    @Override
    @Transactional
    public EmployeeDTO transferDepartmentAndAdjustSalary(String id, String newDepartment, Double salaryIncrement) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // Atomic changes inside MongoDB session transaction
        employee.setDepartment(newDepartment);
        employee.setSalary(employee.getSalary() + salaryIncrement);

        Employee saved = employeeRepository.save(employee);
        return convertToDTO(saved);
    }

    // Helper mappings
    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        return employee;
    }
}