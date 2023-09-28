package database;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import static java.sql.DriverManager.getConnection;

public class Database {
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "root@123lol";
    public static final String URL = "jdbc:mysql://localhost:3306/mysql";
    public static Statement statement;
    public static Connection connection;


    static {
        try {
            connection = getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }


    public static void addAnimal() {
    }

    public static void deleteAnimalById() {
    }

    public static void reviewSkillsById() {
    }

    public static void trainSkillsById() {
    }
}

