import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final Hotel hotel =
            new Hotel("Grand Horizon Hotel");

    public static void main(String[] args) {

        showWelcome();

        boolean running = true;

        while (running) {

            showMenu();

            System.out.print(
                    "Enter your choice: "
            );

            String input =
                    scanner.nextLine().trim();

            int choice;

            try {

                choice =
                        Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println();
                System.out.println(
                        "Invalid input. Please enter a number from 1 to 8."
                );

                continue;
            }

            switch (choice) {

                case 1:
                    hotel.displayAllRooms();
                    break;

                case 2:
                    searchRooms();
                    break;

                case 3:
                    hotel.createBooking(scanner);
                    break;

                case 4:
                    hotel.viewBooking(scanner);
                    break;

                case 5:
                    hotel.cancelBooking(scanner);
                    break;

                case 6:
                    hotel.displayAllBookings();
                    break;

                case 7:
                    showAbout();
                    break;

                case 8:
                    running = false;
                    showExitMessage();
                    break;

                default:

                    System.out.println();
                    System.out.println(
                            "Invalid choice. Please select 1-8."
                    );
            }
        }

        scanner.close();
    }

    /*
     * Welcome screen.
     */
    private static void showWelcome() {

        System.out.println();
        System.out.println(
                "======================================================"
        );

        System.out.println(
                "           GRAND HORIZON HOTEL"
        );

        System.out.println(
                "             RESERVATION SYSTEM"
        );

        System.out.println(
                "======================================================"
        );

        System.out.println(
                "        Welcome! Manage your stay with ease."
        );

        System.out.println(
                "======================================================"
        );
    }

    /*
     * Main menu.
     */
    private static void showMenu() {

        System.out.println();
        System.out.println(
                "------------------- MAIN MENU -------------------"
        );

        System.out.println(
                "1. View All Rooms"
        );

        System.out.println(
                "2. Search Available Rooms"
        );

        System.out.println(
                "3. Book a Room"
        );

        System.out.println(
                "4. View Booking Details"
        );

        System.out.println(
                "5. Cancel Reservation"
        );

        System.out.println(
                "6. View All Bookings"
        );

        System.out.println(
                "7. About System"
        );

        System.out.println(
                "8. Exit"
        );

        System.out.println(
                "-------------------------------------------------"
        );
    }

    /*
     * Search rooms by category and dates.
     */
    private static void searchRooms() {

        System.out.println();
        System.out.println(
                "----------- SEARCH AVAILABLE ROOMS -----------"
        );

        System.out.println();
        System.out.println(
                "Room Categories:"
        );

        System.out.println(
                "1. Standard"
        );

        System.out.println(
                "2. Deluxe"
        );

        System.out.println(
                "3. Suite"
        );

        System.out.print(
                "Select category: "
        );

        String choice =
                scanner.nextLine().trim();

        String category;

        switch (choice) {

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

                System.out.println(
                        "Invalid category."
                );

                return;
        }

        LocalDate checkIn =
                readDate(
                        "Enter check-in date (YYYY-MM-DD): "
                );

        LocalDate checkOut =
                readDate(
                        "Enter check-out date (YYYY-MM-DD): "
                );

        if (!checkOut.isAfter(checkIn)) {

            System.out.println(
                    "Check-out date must be after check-in date."
            );

            return;
        }

        hotel.searchRooms(
                category,
                checkIn,
                checkOut
        );
    }

    /*
     * Reads a valid date from the user.
     */
    private static LocalDate readDate(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            try {

                return LocalDate.parse(input);

            } catch (Exception e) {

                System.out.println(
                        "Invalid date. Use YYYY-MM-DD."
                );
            }
        }
    }

    /*
     * Displays project information.
     */
    private static void showAbout() {

        System.out.println();
        System.out.println(
                "======================================================"
        );

        System.out.println(
                "                 ABOUT THE SYSTEM"
        );

        System.out.println(
                "======================================================"
        );

        System.out.println(
                "Project       : Hotel Reservation System"
        );

        System.out.println(
                "Hotel         : " + hotel.getHotelName()
        );

        System.out.println(
                "Language      : Java"
        );

        System.out.println(
                "Architecture  : Object-Oriented Programming"
        );

        System.out.println(
                "Storage       : File I/O"
        );

        System.out.println(
                "Payment       : Simulated Payment"
        );

        System.out.println();
        System.out.println(
                "Features:"
        );

        System.out.println(
                "- Room categorization"
        );

        System.out.println(
                "- Room availability search"
        );

        System.out.println(
                "- Room reservations"
        );

        System.out.println(
                "- Booking cancellation"
        );

        System.out.println(
                "- Booking details"
        );

        System.out.println(
                "- Payment simulation"
        );

        System.out.println(
                "- Persistent file storage"
        );

        System.out.println(
                "======================================================"
        );
    }

    /*
     * Exit message.
     */
    private static void showExitMessage() {

        System.out.println();
        System.out.println(
                "======================================================"
        );

        System.out.println(
                "       Thank you for using Grand Horizon Hotel!"
        );

        System.out.println(
                "              Have a wonderful day."
        );

        System.out.println(
                "======================================================"
        );
    }
}
