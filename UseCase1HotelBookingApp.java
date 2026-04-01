import java.util.*;

class Reservation {
    private final String reservationId;
    private final String guestName;
    private final String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingHistory {
    private final List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---\n");
        for (Reservation r : reservations) {
            System.out.println("ID: " + r.getReservationId()
                    + " | Guest: " + r.getGuestName()
                    + " | Room: " + r.getRoomType());
        }
    }

    public void generateSummary(List<Reservation> reservations) {
        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : reservations) {
            summary.put(r.getRoomType(),
                    summary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\n--- Booking Summary ---\n");
        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " Bookings: " + entry.getValue());
        }
    }
}

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=========== Book My Stay ===========");
        System.out.println(" Hotel Booking System v8.0 ");
        System.out.println("===================================");

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("SR-1001", "Arun", "Single Room"));
        history.addReservation(new Reservation("DR-2001", "Priya", "Double Room"));
        history.addReservation(new Reservation("SR-1002", "Rahul", "Single Room"));
        history.addReservation(new Reservation("ST-3001", "Sneha", "Suite Room"));

        BookingReportService reportService = new BookingReportService();

        reportService.displayAllBookings(history.getAllReservations());
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nReporting completed successfully.");
    }
}
