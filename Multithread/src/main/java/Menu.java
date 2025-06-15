import java.io.IOException;
import java.util.List;
import java.util.Scanner;
public class Menu {
    Scanner sc = new Scanner(System.in);
    Manager manager;

    public Menu(Manager manager) {
        this.manager = manager;
    }

    public void menu() throws IOException {
        System.out.println("Выберите действие: ");
        System.out.println("1. Начать рабочий день");
        System.out.println("2. Добавить сотрудника");
        System.out.println("3. Добавить задачу");
        System.out.println("4. Выйти");

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Рабочий день начат");
                return;
            case 2:
                addEmployee();
                break;
            case 3:
                addTask();
                break;
            case 4:
                System.out.println("Программа завершена. До скорых встреч!");
                sc.close();
                return;
            default:
                System.out.println("Выберите действие из списка");
        }
    }

    public void addEmployee() throws IOException {
        System.out.println("Введите имя сотрудника: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Введите длительность рабочего дня сотрудника");
        int totalHours = sc.nextInt();
        manager.addEmployee(name, totalHours);
        System.out.println("Сотрудник был успешно добавлен и готов приступать к выполнению задач!");
    }

    public void addTask() throws IOException {
        int id = manager.getTasks().size() + 1;
        System.out.println("Введите название задачи: ");
        String name = sc.nextLine();
        System.out.println("Введите длительность задачи: ");
        int taskTime = sc.nextInt();
        manager.addTask(id, name, taskTime);
        System.out.println("Задача была успешно добавлена!");
    }
}
