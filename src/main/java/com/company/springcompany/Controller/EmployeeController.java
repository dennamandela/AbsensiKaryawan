package com.company.springcompany.Controller;

import com.company.springcompany.Model.Employee;
import com.company.springcompany.Model.Position;
import com.company.springcompany.Repository.EmployeeRepository;
import com.company.springcompany.Repository.PositionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PositionRepository positionRepository;

    @GetMapping("/addEmployee")
    public List<Employee> findAll() {
        List<Position> listPosition = positionRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listPosition));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return employeeRepository.findAll();
    }

    @PostMapping("/processEmployee")
    public Employee newEmployee(@RequestBody Employee newEmployee) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        Position position = positionRepository.getOne(newEmployee.getPosition().getPositionId());
        newEmployee.setPosition(position);

        return employeeRepository.save(newEmployee);
    }

    @GetMapping("/edit/{id}")
    Employee findOne(@PathVariable Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException());
    }

    @PutMapping("/update/{id}")
    Employee saveOrUpdate(@RequestBody Employee newEmployee, @PathVariable Integer employeeId){

        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    employee.setEmployeeName(newEmployee.getEmployeeName());
                    employee.setEmail(newEmployee.getEmail());
                    employee.setPosition(employee.getPosition());
                    employee.setPassword(employee.getPassword());
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setEmployeeId(employeeId);
                    return employeeRepository.save(newEmployee);
                });
    }

    @DeleteMapping("/delete/{id}")
    void deleteEmployee(@PathVariable Integer employeeId){
        employeeRepository.deleteById(employeeId);
    }
}