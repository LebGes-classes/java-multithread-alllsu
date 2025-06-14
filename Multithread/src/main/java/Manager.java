import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Manager extends Thread {
   private List<Employee> employees;
   private List<Task> tasks;
   private Excel excel;

   public Manager() {
       employees = new ArrayList<>();
       tasks = new ArrayList<>();
       excel = new Excel();
   }

   public void loadExcelData() throws IOException {
       excel.loadData(employees, tasks);
   }

   public void addEmployee(String name, int workTime) {
       Employee employee = new Employee(name, workTime);
       employees.add(employee);
   }

   public void addTask(int taskId, String taskName, int totalHours) {
       Task task = new Task(taskId, taskName, totalHours);
       tasks.add(task);
   }

   //назначаем задачу сотруднику
   public void assignTask() {
       Random random = new Random();
       for (Task task : tasks) {
           if (!employees.isEmpty()) {
               Employee employee = employees.get(random.nextInt(employees.size()));
               task.setResponsibleEmployee(employee);
               employee.addTask(task);
           }
       }
   }

   public void startTasks() throws InterruptedException {
       List<Thread> threads = new ArrayList<>();
       for (Employee employee : employees) {
           Thread thread = new Thread(new TaskThread(employee, employee.getWorkTime()));
           threads.add(thread);
           thread.start();
       }

       for (Thread thread : threads) {
           thread.join();
       }
   }

   public void saveStat() throws IOException {
       excel.makeStatistics(employees, tasks);
   }
}
