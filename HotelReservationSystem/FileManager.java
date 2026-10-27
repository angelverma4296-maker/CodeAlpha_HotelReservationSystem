import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String FILE_NAME = "bookings.txt";

    /*
     * Saves all bookings to bookings.txt.
     */
    public static void saveBookings(
            List<Booking> bookings) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME))) {

            for (Booking booking : bookings) {

                writer.write(
                        booking.toFileString()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error: Unable to save booking data."
            );
        }
    }

    /*
     * Loads all bookings from bookings.txt.
     */
    public static ArrayList<Booking> loadBookings() {

        ArrayList<Booking> bookings =
                new ArrayList<>();

        File file = new File(FILE_NAME);

        /*
         * If the file does not exist,
         * return an empty booking list.
         */
        if (!file.exists()) {
            return bookings;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                /*
                 * Ignore empty lines.
                 */
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                /*
                 * Validate the number of fields.
                 */
                if (data.length != 9) {
                    continue;
                }

                try {

                    String bookingId = data[0];
                    String customerName = data[1];
                    String email = data[2];

                    int roomNumber =
                            Integer.parseInt(data[3]);

                    String roomCategory = data[4];

                    LocalDate checkIn =
                            LocalDate.parse(data[5]);

                    LocalDate checkOut =
                            LocalDate.parse(data[6]);

                    double totalAmount =
                            Double.parseDouble(data[7]);

                    String paymentId = data[8];

                    Booking booking =
                            new Booking(
                                    bookingId,
                                    customerName,
                                    email,
                                    roomNumber,
                                    roomCategory,
                                    checkIn,
                                    checkOut,
                                    totalAmount,
                                    paymentId
                            );

                    bookings.add(booking);

                } catch (Exception e) {

                    System.out.println(
                            "Warning: Invalid booking record skipped."
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error: Unable to read booking data."
            );
        }

        return bookings;
    }
}
