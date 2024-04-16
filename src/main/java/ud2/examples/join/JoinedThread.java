package ud2.examples.join;

class JoinedThread extends Thread {
    private final int delay;

    public JoinedThread(int delay) {
        this.delay = delay;
    }

    @Override
    public void run(){
        try {
            for(int i = 0; i < 5; i++) {
                System.out.printf("El fil %s et saluda per %d vegada.\n",
                        Thread.currentThread().getName(), i
                );
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" interromput.");
        }
    }
}