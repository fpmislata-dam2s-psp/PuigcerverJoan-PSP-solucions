package ud2.exercises.tasks;

public class Task {
    private int duration;
    private String name;
    boolean finished;

    public Task(String name, int duration) {
        this.name = name;
        this.duration = duration;
        finished = false;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void work() throws InterruptedException{
        Thread current = Thread.currentThread();
        System.out.printf("%s: Starting task %s...\n", current.getName(), this.name);

        Thread.sleep(duration);

        setFinished(true);
        System.out.printf("%s: Finished task %s (%d).\n", current.getName(), this.name, this.duration);
    }
}
