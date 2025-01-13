package com.guvi.employee_management.service;

import com.guvi.employee_management.entity.Employee;
import com.guvi.employee_management.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class EmployeeService {

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private final EmployeeRepository employeeRepository;

    public Employee postEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public void deleteEmployee(Long id){
        if(!employeeRepository.existsById(id)){
            throw new EntityNotFoundException("Employee with ID "+ id + " not found");
        }
        employeeRepository.deleteById(id);
    }

    public Employee getEmployeeById(long id){
        return employeeRepository.findById(id).orElse(null);
    }

    public  Employee updateEmployee(Long id, Employee employee){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();

            existingEmployee.setEmailId(employee.getEmailId());
            existingEmployee.setDepartment((employee.getDepartment()));
            existingEmployee.setfName(employee.getfName());
            existingEmployee.setlName(employee.getlName());

            return employeeRepository.save(existingEmployee);
        }
        return null;
    }
}
