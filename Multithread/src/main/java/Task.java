public class Task {
    private int taskId;
    private String taskName;
    private int totalHours; //общее время задачи
    private int remHours; //оставшееся время задачи
    private Employee responsibleEmployee; //сотрудник, выполняющий задачу

    public Task(int taskId, String taskName, int totalHours) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.totalHours = totalHours;
        this.remHours = totalHours; //так как задача только была открыта
    }
    public int getRemHours() {
        return remHours;
    }
    public void setRemHours(int remHours) {
        this. remHours = remHours;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setResponsibleEmployee (Employee responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }
    public void doTask(int time) {
        remHours -= time;
    }

    public static Task newTask(int taskId, String taskName, int totalHours) {
        if (totalHours <= 0) {
            throw new IllegalArgumentException();
        }
        return new Task(taskId, taskName, totalHours);
    }
}
