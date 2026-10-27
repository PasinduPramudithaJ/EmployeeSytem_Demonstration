package com.example.CRUD_BE_MDB.Repository;

import com.example.CRUD_BE_MDB.Model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    // You can define custom query methods here if needed
}
