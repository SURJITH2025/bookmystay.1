import java.util.*;

class Reservation {
    private final String reservationId;
    private final String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {
    private final Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingHistory {
    private final Map<String, Reservation> bookings;

    public BookingHistory() {
        bookings = new HashMap<>();
    }

    public void addBooking(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public Reservation getBooking(String id) {
        return bookings.get(id);
    }

    public void removeBooking(String id) {
        bookings.remove(id);
    }
}

class CancellationService {
    private final RoomInventory inventory;
    private final BookingHistory history;
    private final Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {
        Reservation r = history.getBooking(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found -> " + reservationId);
            return;
        }

        rollbackStack.push(reservationId);

        inventory.increaseAvailability(r.getRoomType());

        history.removeBooking(reservationId);

        System.out.println("Booking Cancelled: " + reservationId);
    }

    public void showRollbackStack() {
        System.out.println("\n--- Rollback Stack ---");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=========== Book My Stay ===========");
        System.out.println(" Hotel Booking System v10.0 ");
        System.out.println("===================================");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        history.addBooking(new Reservation("SR-1001", "Single Room"));
        history.addBooking(new Reservation("DR-2001", "Double Room"));

        CancellationService service = new CancellationService(inventory, history);

        service.cancelBooking("SR-1001");
        service.cancelBooking("XX-9999");

        inventory.displayInventory();
        service.showRollbackStack();

        System.out.println("\nCancellation process completed.");
    }
}
