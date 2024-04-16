package ud2.examples.join;

public class StartJoinedThreads {
    public static void main(String[] args) {
        Thread.currentThread().setName("Fil principal");

        JoinedThread thread1 = new JoinedThread(1000);
        thread1.setName("Fil1");
        JoinedThread thread2 = new JoinedThread(1200);
        thread2.setName("Fil2");
        JoinedThread thread3 = new JoinedThread(1500);
        thread3.setName("Fil3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            System.out.println("Tots els fils han acabat.");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" interromput.");
        }
    }
}