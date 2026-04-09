import java.util.*;

class Reservation {
    private final String guestName;
    private final String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingQueue {
    private final Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

class RoomInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingProcessor extends Thread {
    private final BookingQueue queue;
    private final RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation r;
            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            synchronized (inventory) {
                if (inventory.allocateRoom(r.getRoomType())) {
                    System.out.println(Thread.currentThread().getName() +
                            " confirmed booking for " + r.getGuestName() +
                            " (" + r.getRoomType() + ")");
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " failed booking for " + r.getGuestName() +
                            " (" + r.getRoomType() + ")");
                }
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=========== Book My Stay ===========");
        System.out.println(" Hotel Booking System v11.0 ");
        System.out.println("===================================");

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        queue.addRequest(new Reservation("Arun", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Sneha", "Double Room"));
        queue.addRequest(new Reservation("Kiran", "Double Room"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interruption: " + e.getMessage());
        }

        inventory.displayInventory();

        System.out.println("\nConcurrent booking simulation completed.");
    }
}
