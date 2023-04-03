package dao;

import model.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {
    void create(Employee employee);

    Employee readById(int id);

    List<Employee> readAll();

    void updateEmployeeById(int id, int age);

    void deleteEmployeeById(int id);


}
