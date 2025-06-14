import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Manager manager = new Manager();
        manager.loadExcelData();
        manager.assignTask();
        manager.startTasks();
        manager.saveStat();
        System.out.println("Работа программы завершена ᵔᴗᵔ");
    }
}
