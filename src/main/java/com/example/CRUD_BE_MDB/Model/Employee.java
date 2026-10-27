package com.example.CRUD_BE_MDB.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private String id;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String department;
    
    private Double salary;
}