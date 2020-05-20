package io.helidon.service.employee.impl;

import java.util.List;

import io.helidon.config.Config;

public interface EmployeeRepository {

    public static EmployeeRepository create(String driverType, Config config) {
        switch (driverType) {
        case "Array":
            return new EmployeeRepositoryImpl();
        /*case "Oracle":
            return new EmployeeRepositoryImplDB(config);*/
        default:
            // Array is default
            return new EmployeeRepositoryImpl();
        }
    }

    public List<Employee> getAll();
    public List<Employee> getByLastName(String lastName);
    public List<Employee> getByTitle(String title);
    public List<Employee> getByDepartment(String department);
    public Employee save(Employee employee); // Add new employee
    public Employee update(Employee updatedEmployee, String id);
    public void deleteById(String id);
    public Employee getById(String id);
    public boolean isIdFound(String id);
}

