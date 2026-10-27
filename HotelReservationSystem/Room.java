public class Room {

    private int roomNumber;
    private String category;
    private double pricePerNight;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return String.format(
                "Room %-4d | %-8s | $%.2f/night",
                roomNumber,
                category,
                pricePerNight
        );
    }
}
