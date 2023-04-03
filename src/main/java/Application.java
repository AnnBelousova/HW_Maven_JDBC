import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();

        employeeDAO.updateEmployeeById(2, 55);

        Employee employee = employeeDAO.readById(3);
        System.out.println(employee);

        Employee empl = new Employee("Anna", "Sarova", "femail", 23);
        employeeDAO.create(empl);

        employeeDAO.deleteEmployeeById(4);

        List<Employee> list = new ArrayList<>(employeeDAO.readAll());
        for (Employee e : list) {
            System.out.println(e);
        }
    }
}
