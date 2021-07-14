package com.example.employeeapp;

import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface EmployeeService {
    void addNewEmployee(Employee employee);
    Employee getEmployeeByEmail(String email);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    void editEmployee(int employerId, String firstName, String lastName, String email);
}
