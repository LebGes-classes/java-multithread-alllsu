import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Manager manager = new Manager();
        Menu menu = new Menu(manager);
        manager.loadExcelData();
        menu.menu();
        int day = 1;
        while (!manager.taskComp()) {
            System.out.println("День " + day + " начался");
            manager.assignTask();
            manager.startTasks();
            Thread.sleep(2000);
            manager.saveStat(day);
            System.out.println("День " + day + " завершён ᵔᴗᵔ");
            Thread.sleep(1000);
            day++;
        }
        System.out.println("Все задачи завершены!");
    }
}