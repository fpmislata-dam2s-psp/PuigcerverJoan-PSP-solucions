package ud2.practices.collectivetasks;

import java.util.ArrayList;
import java.util.List;

public class EmployeeThread extends Thread{

    Team team;
    public EmployeeThread(String name) {
        super();
        this.setName(name);
        this.team = null;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public void run() {
        /**
         * TODO: Get next task from team.
         *
         * If the task needs to be done:
         *  - Do the task with work()
         *  - Add the task to testingTasks
         * If the task needs to be tested:
         *  - Test the task with test()
         *  - Add the task to finishedTasks
         * If the task is null:
         *  - All tasks are done. Exit.
         */
        try {
            while (true){
                Task t = team.getNextTask();
                if (t == null) {
                    break;
                } else if (t.status() == TaskStatus.UNFINISHED){
                    t.work();
                    team.addTestingTask(t);
                } else if (t.status() == TaskStatus.TESTING) {
                    t.test();
                    team.addFinishedTask(t);
                }
            }
            System.out.printf("%s: Ha realitzat totes les tasques assignades.\n", this.getName());
        } catch (InterruptedException e) {
            System.out.printf("%s: Ha segut interromput.\n", this.getName());
        }

    }
}
