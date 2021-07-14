package com.example.employeeapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addNewEmployee(Employee employee) {
//        employeeRepository.save(employee);
        Optional<Employee> potentialEmployer = employeeRepository.findByEmail(employee.getEmail());
        if (potentialEmployer.isPresent()) {
            throw new IllegalStateException("This email already taken");
        }
        if(employee.getFirstName().matches("[a-zA-Z]+") && employee.getLastName().matches("[a-zA-Z]+"))
            employeeRepository.save(employee);
        else
            throw new IllegalStateException("The first name and the last name must contain alphabetical characters only");
    }

    @Override
    public /*private*/ Employee getEmployeeById(int id) {
        Optional<Employee> potentialEmployer = employeeRepository.findById(id);
        if (potentialEmployer.isPresent()) {
            return potentialEmployer.get();
        } else {
            throw new IllegalStateException("There no employee with such ID: " + id);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , "There no employee with such ID: " + id);
        }
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        Optional<Employee> potentialEmployer = employeeRepository.findByEmail(email);
        if (potentialEmployer.isPresent()) {
            return potentialEmployer.get();
        } else {
            throw new IllegalStateException("There no employee with such email: " + email);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , "There no employee with such email: " + email);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    @Override
    public void editEmployee(int employerId, String firstName, String lastName, String email) {

        Employee employee = getEmployeeById(employerId);
        if(firstName != null && firstName.length()>0 && !firstName.equals(employee.getFirstName()))
        {
            if(!firstName.matches("[a-zA-Z]+"))
                throw new IllegalStateException("The first name must contain alphabetical characters only");
            else
                employee.setFirstName(firstName);
        }
        if(lastName != null && lastName.length()>0 && !lastName.equals(employee.getLastName()))
            if(!lastName.matches("[a-zA-Z]+"))
                throw new IllegalStateException("The last name must contain alphabetical characters only");
            else
                employee.setLastName(lastName);
        if(email != null && email.length() > 0 && !email.equals(employee.getEmail()))
        {
            Optional<Employee> curPotentialEmployer = employeeRepository.findByEmail(email);
            if (curPotentialEmployer.isEmpty() && email.contains("@")) {
                employee.setEmail(email);
            } else {
                throw new IllegalStateException("This email already taken");
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , "This email already taken, or '@' must be in a email address");
            }
        }
    }
}
