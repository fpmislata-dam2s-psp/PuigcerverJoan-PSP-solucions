package ud2.exercises.tasks;

public class ManagerThread extends Thread{
    private Team team;

    public ManagerThread(Team team) {
        super();
        this.team = team;
        this.setName(String.format("%sManager", team.getName()));
    }

    @Override
    public void run() {
        System.out.printf("%s: L'equip %s ha començat a treballar.\n", this.getName(), team.getName());

        // TODO: Make all your assigned employees do their tasks
        for(EmployeeThread employeeThread : team.getEmployees()){
            employeeThread.start();
        }

        for(EmployeeThread employeeThread : team.getEmployees()){
            try {
                employeeThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.printf("%s: L'equip %s ha realitzat totes les tasques.\n", this.getName(), team.getName());
    }
}
