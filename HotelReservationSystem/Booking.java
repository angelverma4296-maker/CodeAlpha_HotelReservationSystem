import java.time.LocalDate;

public class Booking {

    private String bookingId;
    private String customerName;
    private String email;
    private int roomNumber;
    private String roomCategory;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalAmount;
    private String paymentId;

    public Booking(
            String bookingId,
            String customerName,
            String email,
            int roomNumber,
            String roomCategory,
            LocalDate checkIn,
            LocalDate checkOut,
            double totalAmount,
            String paymentId) {

        this.bookingId = bookingId;
        this.customerName = customerName;
        this.email = email;
        this.roomNumber = roomNumber;
        this.roomCategory = roomCategory;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.paymentId = paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    /*
     * Checks whether this booking overlaps
     * with another booking.
     */
    public boolean overlaps(
            LocalDate requestedCheckIn,
            LocalDate requestedCheckOut) {

        return requestedCheckIn.isBefore(checkOut)
                && requestedCheckOut.isAfter(checkIn);
    }

    /*
     * Converts booking data into a CSV-style line
     * for storing inside bookings.txt.
     */
    public String toFileString() {

        return bookingId + "|"
                + customerName + "|"
                + email + "|"
                + roomNumber + "|"
                + roomCategory + "|"
                + checkIn + "|"
                + checkOut + "|"
                + totalAmount + "|"
                + paymentId;
    }

    /*
     * Displays complete booking information.
     */
    public void displayDetails() {

        System.out.println();
        System.out.println("==============================================");
        System.out.println("             BOOKING DETAILS");
        System.out.println("==============================================");

        System.out.println("Booking ID      : " + bookingId);
        System.out.println("Customer Name   : " + customerName);
        System.out.println("Email           : " + email);
        System.out.println("Room Number     : " + roomNumber);
        System.out.println("Room Category   : " + roomCategory);
        System.out.println("Check-in Date   : " + checkIn);
        System.out.println("Check-out Date  : " + checkOut);
        System.out.printf("Total Amount    : $%.2f%n", totalAmount);
        System.out.println("Payment ID      : " + paymentId);

        System.out.println("==============================================");
    }
}