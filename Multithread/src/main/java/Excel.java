import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.*;

public class Excel {
    public static final String INPUT_FILE = "C:\\Users\\user\\Documents\\Homework\\Multithread\\Input.xlsx";
    public static final String OUTPUT_FILE = "C:\\Users\\user\\Documents\\Homework\\Multithread\\Output day%d.xlsx";

    public void loadData(List<Employee> employees, List<Task> tasks) throws IOException {
        try (FileInputStream fis = new FileInputStream(INPUT_FILE);
             Workbook workbook = new XSSFWorkbook(fis)) {
            //читаем данные о задачах
            Map<Integer, Task> taskMap = new HashMap<>();
            Sheet taskSheet = workbook.getSheet("Task");
            if (taskSheet == null) {
                throw new IllegalArgumentException("Лист Task не найден");
            }
            for (Row row : taskSheet) {
                if (row.getRowNum() == 0) continue; //пропускаем заголовки
                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                int totalHours = (int) row.getCell(2).getNumericCellValue();
                Task task = Task.newTask(id, name, totalHours);
                taskMap.put(id, task);
                tasks.add(task);
            }

            //читаем данные о сотрудниках
            Map<String, Employee> employeeMap = new HashMap<>();
            Sheet employeeSheet = workbook.getSheet("Employee");
            if (employeeSheet == null) {
                throw new IllegalArgumentException("Лист Employee не найден");
            }
            for (Row row : employeeSheet) {
                if (row.getRowNum() == 0) continue; //пропускаем заголовки
                String name = row.getCell(0).getStringCellValue();
                int workTime = (int) row.getCell(1).getNumericCellValue();

                //создаём сотрудника, если его ещё нет
                Employee employee = employeeMap.get(name);
                if (employee == null) {
                    employee = new Employee(name, workTime);
                    employees.add(employee);
                    employeeMap.put(name, employee);
                }
            }
        }
    }

    public void makeStatistics(List<Employee> employees, List<Task> tasks, int day) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet statSheet = workbook.createSheet("Statistics");

            //заполняем файл после рабочего дня
            Row headerRow = statSheet.createRow(0);
            String[] headers = {"Employee Name", "Total Hours", "Work Hours", "Rest Hours", "Efficiency"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }
            int rowNum = 1;
            for (Employee employee : employees) {
                Row row = statSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employee.getName());
                row.createCell(1).setCellValue(employee.getWorkHours() + employee.getRestHours());
                row.createCell(2).setCellValue(employee.getWorkHours());
                row.createCell(3).setCellValue(employee.getRestHours());
                row.createCell(4).setCellValue(employee.getEfficiency() + "%");
            }

            Sheet tasksSheet = workbook.createSheet("Task States");
            Row header = tasksSheet.createRow(0);
            header.createCell(0).setCellValue("Task ID");
            header.createCell(1).setCellValue("Remaining Hours");

            for (int i = 0; i < tasks.size(); i++) {
                Row row = tasksSheet.createRow(i+1);
                row.createCell(0).setCellValue(tasks.get(i).getTaskId());
                row.createCell(1).setCellValue(tasks.get(i).getRemHours());
            }
            try (FileOutputStream fos = new FileOutputStream(String.format(OUTPUT_FILE, day))) {
                workbook.write(fos);
            }
        }
    }
    public void updateFile(String empName, int workTime, String taskName, int taskId, int taskHours) throws IOException {
        try(FileInputStream fis = new FileInputStream(INPUT_FILE);
        Workbook workbook = new XSSFWorkbook(fis)) {
            if (empName != null && workTime > 0 && workTime <= 8) {
                Sheet empSheet = workbook.getSheet("Employee");
                int lastRow = empSheet.getLastRowNum();
                Row newRow = empSheet.createRow(lastRow + 1);
                newRow.createCell(0).setCellValue(empName);
                newRow.createCell(1).setCellValue(workTime);
            }
            if (taskName != null && taskId > 0 && taskHours > 0 && taskHours <= 12) {
                Sheet taskSheet = workbook.getSheet("Task");
                int lastRow = taskSheet.getLastRowNum();
                Row newRow = taskSheet.createRow(lastRow + 1);
                newRow.createCell(0).setCellValue(taskId);
                newRow.createCell(1).setCellValue(taskName);
                newRow.createCell(2).setCellValue(taskHours);
            }

            try (FileOutputStream fos = new FileOutputStream(INPUT_FILE)) {
                workbook.write(fos);
            }
        }


    }
}
