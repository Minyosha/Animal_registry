package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;

import static database.Database.connection;
import static database.Database.statement;

public class Counter implements AutoCloseable {

    static int sum;
    {
        sum = 0;
    }

    public void add() throws ClassNotFoundException, SQLException {
        sum++;
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE counter SET counter = counter + 1 WHERE id = 1;");
            preparedStatement1.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

        } catch (InputMismatchException | SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            System.out.println("Счетчик +1 записан в БД");
        }

    }

    @Override
    public void close() {
        System.out.println("Счетчик отработал");
    }
}
