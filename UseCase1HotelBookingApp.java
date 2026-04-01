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
    private final Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue ---\n");
        for (Reservation r : queue) {
            System.out.println("Guest: " + r.getGuestName() + " | Room: " + r.getRoomType());
        }
    }
}

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=========== Book My Stay ===========");
        System.out.println(" Hotel Booking System v5.0 ");
        System.out.println("===================================");

        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addRequest(new Reservation("Arun", "Single Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Double Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Sneha", "Single Room"));

        bookingQueue.displayQueue();

        System.out.println("\nRequests are queued in FIFO order.");
    }
}
