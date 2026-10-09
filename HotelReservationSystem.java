import java.util.ArrayList;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean available;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class Reservation {
    private int reservationId;
    private String guestName;
    private Room room;

    public Reservation(int reservationId, String guestName, Room room) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.room = room;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }
}

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> bookings = new ArrayList<>();
    static int nextId = 1001;

    public static void main(String[] args) {

        rooms.add(new Room(101, "Standard", 5000));
        rooms.add(new Room(102, "Standard", 5000));
        rooms.add(new Room(201, "Deluxe", 8000));
        rooms.add(new Room(202, "Deluxe", 8000));
        rooms.add(new Room(301, "Suite", 12000));
        rooms.add(new Room(302, "Suite", 12000));

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    showAvailableRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    cancelReservation();
                    break;
                case 4:
                    showBookingDetails();
                    break;
                case 5:
                    System.out.println("Thank you for using our hotel system!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    static void showAvailableRooms() {
        System.out.println("\n----- AVAILABLE ROOMS -----");
        boolean found = false;

        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.printf(
                    "Room %d | %s | Rs. %.2f%n",
                    room.getRoomNumber(),
                    room.getCategory(),
                    room.getPrice()
                );
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    static void bookRoom() {
        showAvailableRooms();

        System.out.print("\nEnter room number to book: ");
        int number = readInt();

        Room selectedRoom = null;

        for (Room room : rooms) {
            if (room.getRoomNumber() == number
                    && room.isAvailable()) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room is unavailable or invalid!");
            return;
        }

        System.out.print("Enter guest name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Guest name cannot be empty!");
            return;
        }

        System.out.println("\n----- PAYMENT -----");
        System.out.printf("Amount to pay: Rs. %.2f%n",
                selectedRoom.getPrice());
        System.out.print("Confirm payment? (yes/no): ");

        String payment = sc.nextLine().trim();

        if (!payment.equalsIgnoreCase("yes")) {
            System.out.println("Payment cancelled. No booking made.");
            return;
        }

        // Simulated payment
        System.out.println("Processing payment...");
        System.out.println("Payment successful!");

        Reservation reservation =
                new Reservation(nextId++, name, selectedRoom);

        bookings.add(reservation);
        selectedRoom.setAvailable(false);

        System.out.println("\nBooking successful!");
        System.out.println("Reservation ID: "
                + reservation.getReservationId());
        System.out.println("Guest: "
                + reservation.getGuestName());
        System.out.println("Room: "
                + selectedRoom.getRoomNumber());
    }

    static void cancelReservation() {
        System.out.print("Enter Reservation ID: ");
        int id = readInt();

        for (int i = 0; i < bookings.size(); i++) {
            Reservation reservation = bookings.get(i);

            if (reservation.getReservationId() == id) {
                reservation.getRoom().setAvailable(true);
                bookings.remove(i);

                System.out.println(
                    "Reservation cancelled successfully!"
                );
                return;
            }
        }

        System.out.println("Reservation ID not found!");
    }

    static void showBookingDetails() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n----- BOOKING DETAILS -----");

        for (Reservation reservation : bookings) {
            Room room = reservation.getRoom();

            System.out.println(
                "Reservation ID: "
                + reservation.getReservationId()
            );
            System.out.println(
                "Guest Name: " + reservation.getGuestName()
            );
            System.out.println(
                "Room Number: " + room.getRoomNumber()
            );
            System.out.println(
                "Category: " + room.getCategory()
            );
            System.out.printf(
                "Amount: Rs. %.2f%n", room.getPrice()
            );
            System.out.println("---------------------------");
        }
    }
}
