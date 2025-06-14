public class TaskThread  implements Runnable{
    private Employee employee;
    private int workTime;

    public TaskThread(Employee employee, int workTime) {
        this.employee = employee;
        this.workTime = workTime;
    }

    @Override
    public void run() {
        int maxHours = employee.getWorkTime();
        int workTime = 0;
        for (Task task : employee.getTasks()) {
            while (task.getRemHours() > 0 && workTime < maxHours) {
                int taskTime = Math.min(task.getRemHours(), maxHours - workTime);
                task.doTask(taskTime); //уменьшаем оставшееся время на выполнение задачи
                workTime += taskTime;
                employee.incWorkHours(taskTime);
            }
        }
        employee.incRestHours(maxHours - workTime);
    }
}
