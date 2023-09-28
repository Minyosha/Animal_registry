package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static database.Database.connection;
import static database.Database.statement;

public class CheckInput {
    public static int choice() {
        Scanner scanner = new Scanner(System.in);
        int num;
        while (true) {
            try {
                num = Integer.parseInt(scanner.nextLine());
                if (num < 1) {
                    System.out.println("\n\tОшибка: число должно быть больше 0");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n\tОшибка: введите число");
            }
        }
        return num;
    }

    public static boolean checkId(int checkInt) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");
        try {
            StringBuilder builder = new StringBuilder("SELECT Biological_species, Id, Name, Birthday FROM animals WHERE animals.id = ");
            builder.append(checkInt);
            builder.append(";");
            String result = builder.toString();
            ResultSet resultSet = statement.executeQuery(result);
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (InputMismatchException | SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}