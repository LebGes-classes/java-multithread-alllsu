import java.util.*;

public class Employee {
    private String name;
    private List<Task> tasks;
    private int workHours; //время, проведённое за работой
    private int restHours; //часы простоя
    private int workTime; //длительность рабочего дня


    public Employee(String name, int workTime) {
        this.tasks = new ArrayList<>();
        this.name = name;
        this.workHours = 0;
        this.restHours = 0;
        this.workTime = workTime;
    }
    //геттеры
    public String getName() {
        return name;
    }
    public int getWorkTime() {
        return workTime;
    }
    public int getWorkHours() {
        return workHours;
    }
    public int getRestHours() {
        return restHours;
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void incWorkHours(int hours) {
        workHours += hours;
    }

    public void incRestHours(int hours) {
        restHours += hours;
    }

    public double getEfficiency() {
        return Math.round(((double) workHours / (workHours + restHours)) * 100);
    }

    public static Employee newEmployee(String name, int time) {
        if (time <= 0 || time > 8) {
            throw new IllegalArgumentException("Ошибка: длительность рабочего дня не может превышать 8 часов или быть отрицательной");
        } else {
          return new Employee(name, time);
        }
    }
}
