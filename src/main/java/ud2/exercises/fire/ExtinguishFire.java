package ud2.exercises.fire;

public class ExtinguishFire {
    public static void main(String[] args) throws InterruptedException {
        SwimmingPool swimmingPool = new SwimmingPool(1000);
        Fire fire = new Fire(2000);

        Helicopter helicopter = new Helicopter(500, swimmingPool, fire);

        BucketPerson bucketPerson1 = new BucketPerson(10, 500, swimmingPool);
        BucketPerson bucketPerson2 = new BucketPerson(20, 1300, swimmingPool);
        BucketPerson bucketPerson3 = new BucketPerson(500, 2100, swimmingPool);

        helicopter.start();
        bucketPerson1.start();
        bucketPerson2.start();
        bucketPerson3.start();

        helicopter.join();

        bucketPerson1.interrupt();
        bucketPerson2.interrupt();
        bucketPerson3.interrupt();

        bucketPerson1.join();
        bucketPerson2.join();
        bucketPerson3.join();

        System.out.println("Fire extinguished");
    }
}
