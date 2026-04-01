abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    
    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public abstract void displayDetails();
}

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    @Override
    public void displayDetails() {
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
    }
}
