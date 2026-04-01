
=======

import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private final Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void reduceAvailability(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        int current = inventory.get(roomType);

        if (current <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }

        inventory.put(roomType, current - 1);
    }
}

class BookingValidator {

    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        if (inventory.getAvailability(roomType) == -1) {
            throw new InvalidBookingException("Room type does not exist");
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Room not available");
=======


import java.util.*;

class Reservation {
    private final String reservationId;

    private final String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

=======
    private final String guestName;
    private final String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
=======

import java.util.*;

class AddOnService {
    private final String serviceName;
    private final double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private final Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public void displayServices(String reservationId) {
        List<AddOnService> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());

        System.out.println("\nServices for Reservation ID: " + reservationId);

        double total = 0;

        for (AddOnService s : services) {
            System.out.println(s.getServiceName() + " - ₹" + s.getCost());
            total += s.getCost();
        }

        System.out.println("Total Add-On Cost: ₹" + total);
    }
}

public class UseCase7AddOnServiceSelection {
=======

=======


import java.util.*;

class Reservation {
    private final String guestName;
    private final String roomType;

    public Reservation(String guestName, String roomType) {

        this.guestName = guestName;
        this.roomType = roomType;
    }



    public String getReservationId() {
        return reservationId;
    }


=======
=======

    public String getGuestName() {
        return guestName;
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
=======

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
=======
class BookingQueue {
    private final Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }


    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
=======
    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue ---\n");
        for (Reservation r : queue) {
            System.out.println("Guest: " + r.getGuestName() + " | Room: " + r.getRoomType());
        }
    }
}

public class UseCase5BookingRequestQueue {
=======

import java.util.*;



import java.util.HashMap;
import java.util.Map;

class RoomInventory {


    private final Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
=======
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);

    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }


    public void reduceAvailability(String roomType) {
        int current = inventory.getOrDefault(roomType, 0);
        if (current > 0) {
            inventory.put(roomType, current - 1);

        }
    }
}

class BookingService {
    private final RoomInventory inventory;


    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guestName, String roomType) {
        BookingValidator validator = new BookingValidator();

        try {
            validator.validate(guestName, roomType, inventory);

            inventory.reduceAvailability(roomType);

            System.out.println("Booking successful for " + guestName + " in " + roomType);

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

public class UseCase9ErrorHandlingValidation {
=======
    private final Map<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    public void processBookings(BookingQueue queue) {
        while (!queue.isEmpty()) {
            Reservation reservation = queue.getNextRequest();
            String roomType = reservation.getRoomType();

            if (inventory.getAvailability(roomType) > 0) {
                String roomId = generateRoomId(roomType);

                allocatedRooms.putIfAbsent(roomType, new HashSet<>());
                allocatedRooms.get(roomType).add(roomId);

                inventory.reduceAvailability(roomType);

                System.out.println("Booking Confirmed -> Guest: " + reservation.getGuestName()
                        + " | Room: " + roomType + " | Room ID: " + roomId);
            } else {
                System.out.println("Booking Failed (No Availability) -> Guest: "
                        + reservation.getGuestName() + " | Room: " + roomType);
            }
        }
    }

    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}

public class UseCase6RoomAllocationService {
=======
    public void updateAvailability(String roomType, int count) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + count);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class UseCase3InventorySetup {





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
=======

        System.out.println(" Hotel Booking System v9.0 ");
        System.out.println("===================================");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        service.bookRoom("Arun", "Single Room");
        service.bookRoom("", "Double Room");
        service.bookRoom("Priya", "Suite Room");
        service.bookRoom("Rahul", "Luxury Room");

        System.out.println("\nValidation process completed.");
=======

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
=======

        System.out.println(" Hotel Booking System v7.0 ");
        System.out.println("===================================");

        String reservationId1 = "SR-1001";
        String reservationId2 = "DR-2001";

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(reservationId1, new AddOnService("Breakfast", 300));
        manager.addService(reservationId1, new AddOnService("WiFi", 150));
        manager.addService(reservationId1, new AddOnService("Airport Pickup", 500));

        manager.addService(reservationId2, new AddOnService("Dinner", 400));
        manager.addService(reservationId2, new AddOnService("Spa", 800));

        manager.displayServices(reservationId1);
        manager.displayServices(reservationId2);

        System.out.println("\nAdd-on services processed successfully.");
=======
        System.out.println(" Hotel Booking System v3.0 ");
        System.out.println("===================================");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nUpdating inventory...");

        inventory.updateAvailability("Single Room", -1);
        inventory.updateAvailability("Suite Room", +1);

        inventory.displayInventory();

        System.out.println("\nApplication executed successfully.");


abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;




    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }


    public String getRoomType() {
        return roomType;
    }

=======

    public abstract void displayDetails();
}

class SingleRoom extends Room {

=======

    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    @Override
    public void displayDetails() {

        System.out.println("Room Type: " + roomType + ", Beds: " + beds + ", Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
=======
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}


class DoubleRoom extends Room {


    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    @Override
    public void displayDetails() {

        System.out.println("Room Type: " + roomType + ", Beds: " + beds + ", Price: ₹" + price);
    }
}

class SuiteRoom extends Room {

        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}


class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    @Override
    public void displayDetails() {

        System.out.println("Room Type: " + roomType + ", Beds: " + beds + ", Price: ₹" + price);
    }
}

class RoomInventory {
    private final Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("\n--- Available Rooms ---\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------------");
            }
        }
    }
}

public class UseCase4RoomSearch {



    public static void main(String[] args) {

        System.out.println("=========== Book My Stay ===========");

        System.out.println(" Hotel Booking System v6.0 ");
        System.out.println("===================================");

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        queue.addRequest(new Reservation("Arun", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Double Room"));
        queue.addRequest(new Reservation("Rahul", "Suite Room"));
        queue.addRequest(new Reservation("Sneha", "Single Room"));

        BookingService service = new BookingService(inventory);

        service.processBookings(queue);

        System.out.println("\nAll requests processed.");
=======

        System.out.println(" Hotel Booking System v5.0 ");
        System.out.println("===================================");

        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addRequest(new Reservation("Arun", "Single Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Double Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Sneha", "Single Room"));

        bookingQueue.displayQueue();

        System.out.println("\nRequests are queued in FIFO order.");
=======
        System.out.println(" Hotel Booking System v4.0 ");
        System.out.println("===================================");

        RoomInventory inventory = new RoomInventory();

        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        RoomSearchService searchService = new RoomSearchService();

        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nSearch completed successfully.");
=======
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

public class UseCase2RoomInitialization {

    public static void main(String[] args) {


        System.out.println("=========== Book My Stay ===========");
        System.out.println(" Hotel Booking System v2.0 ");
        System.out.println("===================================");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

       
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

       
        System.out.println("\n--- Room Details ---\n");

        single.displayDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println("-----------------------------------");

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("-----------------------------------");

        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("-----------------------------------");

        System.out.println("Application executed successfully.");
=======
        System.out.println
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v1.0 ");
        System.out.println
        System.out.println("Your journey to comfortable stays begins here!");
        System.out.println("Application started successfully.");




    }
}
