package view;

public class MainView {
    public void showMenu() {
        System.out.println("Приют животных. Выберите действие:");
        System.out.println("1. Показать всех животных");
        System.out.println("2. Добавить животное");
        System.out.println("3. Удалить животное по id");
        System.out.println("4. Посмотреть что умеет животное по id");
        System.out.println("5. Научить животное новым командам по id");
        System.out.println("6. Заставить животное разучить изученную команду по id");
        System.out.println("7. Удалить все данные из базы");
        System.out.println("8. Создать таблицы в базе данных");
        System.out.println("0. Выход");
    }
}