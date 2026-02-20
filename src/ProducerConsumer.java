class SharedResource {
    private int item;
    private boolean available = false;

    public synchronized void put(int value) {
        try {
            while (available) {
                wait();
            }

            item = value;
            System.out.println("Produced: " + item);
            available = true;

            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void get() {
        try {
            while (!available) {
                wait();
            }

            System.out.println("Consumed: " + item);
            available = false;

            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Producer extends Thread {
    SharedResource resource;

    Producer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.put(i);
        }
    }
}

class Consumer extends Thread {
    SharedResource resource;

    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.get();
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Producer p = new Producer(resource);
        Consumer c = new Consumer(resource);

        p.start();
        c.start();
    }
}