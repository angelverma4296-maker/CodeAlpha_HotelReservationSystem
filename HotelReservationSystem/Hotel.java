import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Hotel {

    private String hotelName;
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;

    public Hotel(String hotelName) {
        this.hotelName = hotelName;
        rooms = new ArrayList<>();

        /*
         * Load existing bookings from file.
         */
        bookings = FileManager.loadBookings();

        /*
         * Create hotel rooms.
         */
        initializeRooms();
    }

    /*
     * Creates the hotel's room inventory.
     */
    private void initializeRooms() {
        // Standard Rooms
        rooms.add(new Room(101, "Standard", 80));
        rooms.add(new Room(102, "Standard", 80));
        rooms.add(new Room(103, "Standard", 80));

        // Deluxe Rooms
        rooms.add(new Room(201, "Deluxe", 120));
        rooms.add(new Room(202, "Deluxe", 120));
        rooms.add(new Room(203, "Deluxe", 120));

        // Suite Rooms
        rooms.add(new Room(301, "Suite", 200));
        rooms.add(new Room(302, "Suite", 200));
        rooms.add(new Room(303, "Suite", 200));
    }

    /*
     * Displays all rooms.
     */
    public void displayAllRooms() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("                 ROOM LIST");
        System.out.println("==============================================");

        for (Room room : rooms) {
            System.out.println(room);
        }

        System.out.println("==============================================");
    }

    /*
     * Searches available rooms according to
     * category and dates.
     */
    public void searchRooms(String category, LocalDate checkIn, LocalDate checkOut) {
        boolean found = false;

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("            AVAILABLE " + category.toUpperCase() + " ROOMS");
        System.out.println("----------------------------------------------");

        for (Room room : rooms) {
            /*
             * Match the requested category.
             */
            if (!room.getCategory().equalsIgnoreCase(category)) {
                continue;
            }

            /*
             * Check date availability.
             */
            if (isRoomAvailable(room.getRoomNumber(), checkIn, checkOut)) {
                System.out.println(room);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are available for the selected dates.");
        }

        System.out.println("----------------------------------------------");
    }

    /*
     * Checks whether a room is available
     * for a specific date range.
     */
    public boolean isRoomAvailable(int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                if (booking.overlaps(checkIn, checkOut)) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Finds a room by room number.
     */
    private Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    /*
     * Creates a new booking.
     */
    public void createBooking(Scanner scanner) {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("                NEW BOOKING");
        System.out.println("==============================================");

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();

        if (customerName.isEmpty()) {
            System.out.println("Customer name cannot be empty.");
            return;
        }

        System.out.print("Enter email address: ");
        String email = scanner.nextLine().trim();

        if (!isValidEmail(email)) {
            System.out.println("Please enter a valid email address.");
            return;
        }

        LocalDate checkIn = readDate(scanner, "Enter check-in date (YYYY-MM-DD): ");
        LocalDate checkOut = readDate(scanner, "Enter check-out date (YYYY-MM-DD): ");

        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Check-out date must be after check-in date.");
            return;
        }

        System.out.println();
        System.out.println("Select Room Category:");
        System.out.println("1. Standard - $80/night");
        System.out.println("2. Deluxe   - $120/night");
        System.out.println("3. Suite    - $200/night");
        System.out.print("Enter choice: ");

        String categoryChoice = scanner.nextLine().trim();
        String category;

        switch (categoryChoice) {
            case "1":
                category = "Standard";
                break;
            case "2":
                category = "Deluxe";
                break;
            case "3":
                category = "Suite";
                break;
            default:
                System.out.println("Invalid room category.");
                return;
        }

        /*
         * Show rooms available for the selected category and dates.
         */
        searchRooms(category, checkIn, checkOut);

        System.out.print("\nEnter room number to book: ");
        int roomNumber;

        try {
            roomNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number.");
            return;
        }

        Room selectedRoom = findRoom(roomNumber);

        if (selectedRoom == null) {
            System.out.println("Room does not exist.");
            return;
        }

        /*
         * Check that the selected room belongs to the requested category.
         */
        if (!selectedRoom.getCategory().equalsIgnoreCase(category)) {
            System.out.println("The selected room does not belong to the chosen category.");
            return;
        }

        /*
         * Check availability one more time before creating the booking.
         */
        if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
            System.out.println("Sorry, this room is already booked for those dates.");
            return;
        }

        /*
         * Calculate number of nights.
         */
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        /*
         * Calculate total price.
         */
        double totalAmount = nights * selectedRoom.getPricePerNight();

        /*
         * Display booking summary.
         */
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("               BOOKING SUMMARY");
        System.out.println("----------------------------------------------");
        System.out.println("Customer       : " + customerName);
        System.out.println("Room           : " + roomNumber);
        System.out.println("Category       : " + selectedRoom.getCategory());
        System.out.println("Check-in       : " + checkIn);
        System.out.println("Check-out      : " + checkOut);
        System.out.println("Number of nights: " + nights);
        System.out.printf("Price per night : $%.2f%n", selectedRoom.getPricePerNight());
        System.out.printf("Total amount    : $%.2f%n", totalAmount);
        System.out.println("----------------------------------------------");

        /*
         * Payment method.
         */
        System.out.println();
        System.out.println("Select Payment Method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. UPI");
        System.out.print("Enter choice: ");

        String paymentChoice = scanner.nextLine().trim();
        String paymentMethod;

        switch (paymentChoice) {
            case "1":
                paymentMethod = "Credit Card";
                break;
            case "2":
                paymentMethod = "Debit Card";
                break;
            case "3":
                paymentMethod = "UPI";
                break;
            default:
                System.out.println("Invalid payment method.");
                return;
        }

        /*
         * Create and process simulated payment.
         */
        Payment payment = new Payment(totalAmount, paymentMethod);

        if (!payment.processPayment()) {
            System.out.println("Payment failed. Booking cancelled.");
            return;
        }

        /*
         * Generate unique booking ID.
         */
        String bookingId = "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        /*
         * Create Booking object.
         */
        Booking booking = new Booking(
                bookingId,
                customerName,
                email,
                roomNumber,
                selectedRoom.getCategory(),
                checkIn,
                checkOut,
                totalAmount,
                payment.getPaymentId()
        );

        /*
         * Add booking to list.
         */
        bookings.add(booking);

        /*
         * Save booking to file.
         */
        FileManager.saveBookings(bookings);

        /*
         * Display success message.
         */
        System.out.println();
        System.out.println("==============================================");
        System.out.println("             BOOKING SUCCESSFUL!");
        System.out.println("==============================================");
        System.out.println("Booking ID : " + bookingId);
        System.out.println("Payment ID : " + payment.getPaymentId());
        System.out.printf("Amount Paid: $%.2f%n", totalAmount);
        System.out.println();
        System.out.println("Please keep your Booking ID for future reference.");
        System.out.println("==============================================");
    }

    /*
     * Finds and displays a booking.
     */
    public void viewBooking(Scanner scanner) {
        System.out.println();
        System.out.print("Enter Booking ID: ");
        String bookingId = scanner.nextLine().trim();

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        booking.displayDetails();
    }

    /*
     * Cancels an existing booking.
     */
    public void cancelBooking(Scanner scanner) {
        System.out.println();
        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = scanner.nextLine().trim();

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        booking.displayDetails();

        System.out.print("\nAre you sure you want to cancel? (Y/N): ");
        String confirmation = scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Cancellation aborted.");
            return;
        }

        bookings.remove(booking);
        FileManager.saveBookings(bookings);

        System.out.println();
        System.out.println("Booking " + bookingId + " has been cancelled successfully.");
        System.out.println("The room is now available for future bookings.");
    }

    /*
     * Displays all active bookings.
     */
    public void displayAllBookings() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("                ALL BOOKINGS");
        System.out.println("==============================================");

        if (bookings.isEmpty()) {
            System.out.println("There are currently no bookings.");
            System.out.println("==============================================");
            return;
        }

        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.getBookingId());
            System.out.println("Customer  : " + booking.getCustomerName());
            System.out.println("Room      : " + booking.getRoomNumber());
            System.out.println("Category  : " + booking.getRoomCategory());
            System.out.println("Dates     : " + booking.getCheckIn() + " to " + booking.getCheckOut());
            System.out.printf("Amount    : $%.2f%n", booking.getTotalAmount());
            System.out.println("----------------------------------------------");
        }

        System.out.println("==============================================");
    }

    /*
     * Finds a booking by its ID.
     */
    private Booking findBooking(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    /*
     * Reads and validates a date.
     */
    private LocalDate readDate(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid date. Please use YYYY-MM-DD.");
            }
        }
    }

    /*
     * Basic email validation.
     */
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public String getHotelName() {
        return hotelName;
    }

    /*
     * MAIN METHOD: Entry point of the application
     */
    public static void main(String[] args) {
        Hotel myHotel = new Hotel("Grand Java Hotel");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the " + myHotel.getHotelName() + " Booking System");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View All Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View a Booking");
            System.out.println("4. Cancel a Booking");
            System.out.println("5. View All Active Bookings");
            System.out.println("6. Exit");
            System.out.print("Please enter your choice (1-6): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    myHotel.displayAllRooms();
                    break;
                case "2":
                    myHotel.createBooking(scanner);
                    break;
                case "3":
                    myHotel.viewBooking(scanner);
                    break;
                case "4":
                    myHotel.cancelBooking(scanner);
                    break;
                case "5":
                    myHotel.displayAllBookings();
                    break;
                case "6":
                    System.out.println("Exiting the Booking System. Have a great day!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 6.");
            }
        }

        scanner.close();
    }
}

