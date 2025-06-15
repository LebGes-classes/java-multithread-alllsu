import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Manager {
   private List<Employee> employees;
   private List<Task> tasks;
   private Excel excel;

   public Manager() {
       employees = new ArrayList<>();
       tasks = new ArrayList<>();
       excel = new Excel();
   }

    public List<Task> getTasks() {
        return tasks;
    }

    public Excel getExcel() {
        return excel;
    }

    public void loadExcelData() throws IOException {
       excel.loadData(employees, tasks);
   }

   public void addEmployee(String name, int workTime) throws IOException {
       Employee employee = Employee.newEmployee(name, workTime);
       employees.add(employee);
       getExcel().updateFile(name, workTime, null, 0, 0);
   }

   public void addTask(int taskId, String taskName, int totalHours) throws IOException {
       Task task = Task.newTask(taskId, taskName, totalHours);
       tasks.add(task);
       getExcel().updateFile(null, 0, taskName, taskId, totalHours);
   }

   //назначаем задачу сотруднику
   public void assignTask() {
       Random random = new Random();
       for (Task task : tasks) {
           if (!employees.isEmpty() && task.getRemHours() > 0) {
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

   public void saveStat(int day) throws IOException {
       excel.makeStatistics(employees, tasks, day);
   }

   public boolean taskComp() {
       for (Task task : tasks) {
           if (task.getRemHours() > 0) {
               return false;
           }
       }
       return true;
   }
}
