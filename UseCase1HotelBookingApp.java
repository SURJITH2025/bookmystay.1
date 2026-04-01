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

    public static void main(String[] args) {

        System.out.println("=========== Book My Stay ===========");
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
    }
}
