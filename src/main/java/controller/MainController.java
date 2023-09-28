package controller;

import model.PetType;
import service.CheckInput;
import validator.DateValidatorDateTimeFormatter;
import view.MainView;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static database.Database.connection;
import static database.Database.statement;
import static java.lang.System.in;


public class MainController {
    Scanner scanner = new Scanner(in);

    public void run() throws IOException, SQLException, ClassNotFoundException {
        MainView mainView = new MainView();
        while (true) {
            mainView.showMenu();
            String menuIndex = scanner.nextLine();
            switch (menuIndex) {
                case "0" -> exit();
                case "1" -> printAllAnimals();
                case "2" -> addAnimal();
                case "3" -> deleteAnimalById();
                case "4" -> reviewSkillsById();
                case "5" -> trainSkillsById();
                case "6" -> forgetSkillById();
                case "7" -> clearDB();
                case "8" -> createTables();
                default -> {
                    System.out.println("Ошибка ввода");
                }
            }

        }
    }

    private void exit() throws ClassNotFoundException, SQLException {
        System.out.println("Счетчик за время работы программы сработал " + Counter.sum + " раз");

        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");

        ResultSet resultSet = statement.executeQuery("SELECT id, Counter from counter WHERE (id = 1);");
        while (resultSet.next()) {
            System.out.println("За время работы с базой данных счетчиком пользовались " + resultSet.getString(2) + " раз");
        }

        System.exit(0);
    }

    private void createTables() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement1 = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS Human_friends;");
            PreparedStatement preparedStatement2 = connection.prepareStatement("USE Human_friends;");
            PreparedStatement preparedStatement3 = connection.prepareStatement("CREATE TABLE Animals (\n" +
                    "\tId INT AUTO_INCREMENT PRIMARY KEY, \t\n" +
                    "    Biological_species VARCHAR(20),\n" +
                    "    Name VARCHAR(20), \n" +
                    "    Birthday DATE\n" +
                    ");");
            PreparedStatement preparedStatement4 = connection.prepareStatement("CREATE TABLE Skills (\n" +
                    "\tAnimal_id int,\n" +
                    "    Foreign KEY (Animal_id) REFERENCES Animals (Id) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "    Skill VARCHAR(20)\n" +
                    ");");
            PreparedStatement preparedStatement5 = connection.prepareStatement("CREATE TABLE Counter (id INT AUTO_INCREMENT PRIMARY KEY, Counter int);");
            PreparedStatement preparedStatement6 = connection.prepareStatement("INSERT INTO Counter (Counter) VALUES ('0');");
            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();
            preparedStatement4.executeUpdate();
            preparedStatement5.executeUpdate();
            preparedStatement6.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

        } catch (InputMismatchException | SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            System.out.println("Таблицы созданы");
        }
    }

    private void clearDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement1 = connection.prepareStatement("DROP DATABASE human_friends;");
            preparedStatement1.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

        } catch (InputMismatchException | SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            System.out.println("Таблицы удалены");
        }

    }

    private void forgetSkillById() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");
        System.out.println("Введите id животного для забывания навыка:");
        int idToShowSkills = CheckInput.choice();
        if (CheckInput.checkId(idToShowSkills) == true) {
            System.out.println("Введите навык, который следует забыть");
            String skill = scanner.nextLine();

            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM skills WHERE (animal_id = ?) AND (skill = ?);");
                preparedStatement1.setString(1, String.valueOf(idToShowSkills));
                preparedStatement1.setString(2, String.valueOf(skill));
                preparedStatement1.executeUpdate();
                System.out.println("Животное забыло навык " + skill);
                connection.commit();
                connection.setAutoCommit(true);

            } catch (InputMismatchException | SQLException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        } else {
            System.out.println("Животного с таким id нет в питомнике");
        }


    }

    private void trainSkillsById() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");
        System.out.println("Введите id животного для тренировки навыков:");
        int idToShowSkills = CheckInput.choice();
        if (CheckInput.checkId(idToShowSkills) == true) {
            System.out.println("Введите навык для тренировки");
            String skill = scanner.nextLine();
            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT skill FROM skills WHERE Skills.Animal_id = ?;");
                preparedStatement1.setString(1, String.valueOf(idToShowSkills));
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                int skillsCounter = 0;
                while (resultSet1.next()) {
                    if (skill.equals(resultSet1.getString(1))) {
                        skillsCounter++;
                    }
                }
                if (skillsCounter == 0) {
                    PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO Skills (Animal_id, Skill) VALUES (?,?);");
                    preparedStatement2.setInt(1, idToShowSkills);
                    preparedStatement2.setString(2, String.valueOf(skill));
                    preparedStatement2.executeUpdate();
                } else {
                    System.out.println("Животное уже имеет такой навык");
                }
                connection.commit();
                connection.setAutoCommit(true);

            } catch (InputMismatchException | SQLException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        } else {
            System.out.println("Животного с таким id нет в питомнике");
        }

    }

    private void reviewSkillsById() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");
        System.out.println("Введите id животного для просмотра навыков:");
        int idToShowSkills = CheckInput.choice();
        if (CheckInput.checkId(idToShowSkills) == true) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT id, biological_species, name, birthday FROM animals WHERE id = ?;");
                preparedStatement1.setString(1, String.valueOf(idToShowSkills));
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                while (resultSet1.next()) {
                    System.out.println("Животное с id " + resultSet1.getString(1) + ", вида " + resultSet1.getString(2) + ", по имени " +
                            resultSet1.getString(3) + ", с датой рождения " + resultSet1.getString(4) + " имеет следующие навыки:");
                }

                PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT skill FROM skills WHERE Skills.Animal_id = ?;");
                preparedStatement2.setString(1, String.valueOf(idToShowSkills));
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                int skillsCounter = 0;
                while (resultSet2.next()) {
                    System.out.println(resultSet2.getString(1));
                    skillsCounter++;
                }
                if (skillsCounter == 0) {
                    System.out.println("Животное ничего не умеет");
                }
                connection.commit();
                connection.setAutoCommit(true);

            } catch (InputMismatchException | SQLException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        } else {
            System.out.println("Животного с таким id нет в питомнике");
        }

    }

    private PetType menuChoice() {
        System.out.println("Какое животное добавить?\n1 - Кошка\n2 - Собака\n3 - Хомяк\n0 - Возврат в основное меню");

        while (true) {
            String key = scanner.nextLine();
            switch (key) {
                case "1" -> {
                    return PetType.Cat;
                }
                case "2" -> {
                    return PetType.Dog;
                }
                case "3" -> {
                    return PetType.Hamster;
                }
                case "0" -> {
                    return null;
                }
                default -> System.out.println("Такого варианта нет, введите число от 0 до 3");
            }
        }
    }

    private String addBirthday() {
        System.out.println("Введите дату рождения в формате 'yyyy-mm-dd': ");
        String birthday = scanner.nextLine();
        while (!DateValidatorDateTimeFormatter.isValid(birthday)) {
            System.out.println("Введите дату рождения в формате 'yyyy-mm-dd': ");
            birthday = scanner.nextLine();
        }
        return birthday;
    }

    private void addAnimal() throws ClassNotFoundException, SQLException {
        PetType type = menuChoice();
        System.out.println("Введите имя животного:");
        String name = scanner.nextLine();
        String birthday = addBirthday();
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");
        try (Counter count = new Counter()) {
            connection.setAutoCommit(false);
            PreparedStatement addNewAnimal = connection.prepareStatement("INSERT INTO Animals (Biological_species, Name, Birthday)" +
                    " VALUES (?, ?, ?);");
            addNewAnimal.setString(1, String.valueOf(type));
            addNewAnimal.setString(2, name);
            addNewAnimal.setDate(3, Date.valueOf(birthday));
            addNewAnimal.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            count.add();
        } catch (InputMismatchException | SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

    private void deleteAnimalById() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");
        System.out.println("Введите id животного для удаления:");
        int idToDelete = CheckInput.choice();
        if (CheckInput.checkId(idToDelete) == true) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement removeFromSkillsByID = connection.prepareStatement("DELETE FROM skills WHERE animal_id = " + idToDelete + ";");
                removeFromSkillsByID.executeUpdate();
                PreparedStatement removeFromAnimalsByID = connection.prepareStatement("DELETE FROM animals WHERE id = " + idToDelete + ";");
                removeFromAnimalsByID.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);
            } catch (InputMismatchException | SQLException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        } else {
            System.out.println("Животного с таким id нет в питомнике");
        }

    }


    private void printAllAnimals() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate("USE human_friends;");

        ResultSet resultSet = statement.executeQuery("SELECT animals.id, animals.biological_species, animals.name, animals.birthday, " +
                "GROUP_CONCAT(skills.skill) FROM animals LEFT JOIN skills ON animals.id = skills.animal_id GROUP BY animals.id;");

        System.out.println("Список всех животных");
        System.out.println("ID / Class / Name / Birthdate / Skills");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " / " + resultSet.getString(2) + " / " +
                    resultSet.getString(3) + " / " + resultSet.getString(4) + " / " + resultSet.getString(5));

        }

    }


}
