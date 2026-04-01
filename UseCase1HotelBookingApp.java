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

    public static void main(String[] args) {

        System.out.println("=========== Book My Stay ===========");
        System.out.println(" Hotel Booking System v9.0 ");
        System.out.println("===================================");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        service.bookRoom("Arun", "Single Room");
        service.bookRoom("", "Double Room");
        service.bookRoom("Priya", "Suite Room");
        service.bookRoom("Rahul", "Luxury Room");

        System.out.println("\nValidation process completed.");
    }
}
