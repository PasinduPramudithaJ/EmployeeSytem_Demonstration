package com.example.CRUD_BE_MDB.Controller;

import com.example.CRUD_BE_MDB.DTO.EmployeeDTO;
import com.example.CRUD_BE_MDB.Model.Employee;
import com.example.CRUD_BE_MDB.Service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updated = employeeService.updateEmployee(id, employeeDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // Transactional endpoint: Bulk insertion
    @PostMapping("/bulk")
    public ResponseEntity<List<EmployeeDTO>> bulkCreateEmployees(@Valid @RequestBody List<EmployeeDTO> employeeDTOs) {
        List<EmployeeDTO> createdEmployees = employeeService.bulkCreateEmployees(employeeDTOs);
        return ResponseEntity.ok(createdEmployees);
    }

    // Transactional endpoint: Atomic update (Department Transfer & Salary Adjustment)
    @PatchMapping("/{id}/transfer")
    public ResponseEntity<EmployeeDTO> transferDepartment(
            @PathVariable String id,
            @RequestParam String newDepartment,
            @RequestParam Double salaryIncrement) {
        EmployeeDTO updated = employeeService.transferDepartmentAndAdjustSalary(id, newDepartment, salaryIncrement);
        return ResponseEntity.ok(updated);
    }
}