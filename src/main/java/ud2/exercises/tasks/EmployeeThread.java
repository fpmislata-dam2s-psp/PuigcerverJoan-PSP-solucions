package ud2.exercises.tasks;

import java.util.ArrayList;
import java.util.List;

public class EmployeeThread extends Thread{
    private List<Task> tasks;

    public EmployeeThread(String name) {
        super();
        this.setName(name);
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
    public void addTask(String taskName, int taskDuration) {
        tasks.add(new Task(taskName, taskDuration));
    }
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void run() {
        // TODO: Do all the assigned tasks
        for(Task task : getTasks()){
            try {
                task.work();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.printf("%s: Ha realitzat totes les tasques assignades.\n", this.getName());
    }
}
