package dao;

import java.sql.*;

import model.City;
import model.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    Employee employee;

    @Override
    public void create(Employee employee) {
        final String query = "INSERT INTO employee (first_name, last_name, gender, age) " +
                "VALUES ((?),(?),(?),(?))";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getFirst_name());
            preparedStatement.setString(2, employee.getLast_name());
            preparedStatement.setString(3, employee.getGender());
            preparedStatement.setInt(4, employee.getAge());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee readById(int id) {
        final String query = "SELECT e.id, e.first_name, e.last_name, e.gender, e.age, c.city_name FROM employee e JOIN city c ON c.city_id = e.city_id WHERE id=?;";
        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_e = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                City city = new City(resultSet.getString("city_name"));
                employee = new Employee(id_e, first_name, last_name, gender, age, city);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return employee;
    }

    @Override
    public List<Employee> readAll() {
        List<Employee> employeeList = new ArrayList<>();
        final String query = "SELECT * FROM employee e FULL " +
                "JOIN city c ON c.city_id = e.city_id;";


        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                City city = new City(resultSet.getString("city_name"));
                employee = new Employee(id, first_name, last_name, gender, age, city);
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    @Override
    public void updateEmployeeById(int id, int age) {
        final String query = "UPDATE employee SET age=? WHERE id = ?;";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, age);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployeeById(int id) {
        final String query = "DELETE FROM employee WHERE id = ?;";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        final String user = "postgres";
        final String password = "123";
        final String url = "jdbc:postgresql://localhost:5434/skypro";
        return DriverManager.getConnection(url, user, password);
    }
}
