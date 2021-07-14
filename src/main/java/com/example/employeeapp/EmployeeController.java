package com.example.employeeapp;


import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path = "api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @RequestMapping( value = "/email/{employeeEmail}", method = RequestMethod.GET)
    public Employee getEmployeeByEmail(@PathVariable("employeeEmail") String email){
        return employeeService.getEmployeeByEmail(email);
    }

    @RequestMapping(value = "{employeeId}", method = RequestMethod.GET)
    public Employee getEmployeeById(@PathVariable("employeeId") @NotNull int id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public void addNewEmployee(@RequestBody Employee employee){
        employeeService.addNewEmployee(employee);
    }


    @PutMapping(path = "{employeeID}")
    public void editEmployee(
            @PathVariable("employeeID") int employerId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email){
        employeeService.editEmployee(employerId, firstName, lastName, email);
    }
}
