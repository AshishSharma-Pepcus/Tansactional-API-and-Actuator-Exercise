package com.pepcus.crud.springbootbackend.controller;

import com.pepcus.crud.springbootbackend.exception.ResourceNotFoundException;
import com.pepcus.crud.springbootbackend.model.Addresses;
import com.pepcus.crud.springbootbackend.model.Employee;
import com.pepcus.crud.springbootbackend.repository.AddressRepository;
import com.pepcus.crud.springbootbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping({"/api/v1/employees"})
@Transactional
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/filters/n/{name}")
    public List<Employee> filterByName(@PathVariable String name) {
        List<Employee> filteredEmployeeByName = employeeRepository.findByName(name);
        return filteredEmployeeByName;

    }

    @GetMapping("/filters/p/{phoneNo}")
    public List<Employee> filterByPhoneNo(@PathVariable String phoneNo) {
        List<Employee> filteredEmployeeByPhone = employeeRepository.findByPhoneNo(phoneNo);
        return filteredEmployeeByPhone;
    }

    @GetMapping("filters/e/{emailId}")
    public List<Employee> filterByEmailId(@PathVariable String emailId) {
        List<Employee> filteredEmployeeByEmail = employeeRepository.findByEmailId(emailId);
        return filteredEmployeeByEmail;
    }

    @GetMapping("filters/d/{department}")
    public List<Employee> filterByDepartment(@PathVariable String department) {
        List<Employee> filteredEmployeeByDep = employeeRepository.findByDepartment(department);
        return filteredEmployeeByDep;
    }

    @GetMapping("filters/a/{addressField}")
    public List<Employee> filterByAddressField(@PathVariable String addressField) {
        List<Employee> filteredEmployeeByAddressField = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            for (Addresses address : employee.getAddresses()) {
                if (address.getCountry().equalsIgnoreCase(addressField)
                        || address.getState().equalsIgnoreCase(addressField)
                        || address.getCity().equalsIgnoreCase(addressField)) {
                    filteredEmployeeByAddressField.add(employee);
                }
            }
        }
        return filteredEmployeeByAddressField;
    }

    @GetMapping("/sorts/{field}")
    public List<Employee> sortByEmployeeField(@PathVariable String field) {
        List<Employee> sortedEmployee = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        return sortedEmployee;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails) {
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        updateEmployee.setName(employeeDetails.getName());
        updateEmployee.setPhoneNo(employeeDetails.getPhoneNo());
        updateEmployee.setEmailId(employeeDetails.getEmailId());
        updateEmployee.setAddresses(employeeDetails.getAddresses());

        employeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        employeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}