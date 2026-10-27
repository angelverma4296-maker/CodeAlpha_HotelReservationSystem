import java.util.UUID;

public class Payment {

    private String paymentId;
    private double amount;
    private String paymentMethod;
    private boolean successful;

    public Payment(double amount, String paymentMethod) {

        this.paymentId =
                "PAY-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.successful = false;
    }

    public boolean processPayment() {

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("              PAYMENT PROCESSING");
        System.out.println("----------------------------------------------");

        System.out.printf("Amount         : $%.2f%n", amount);
        System.out.println("Payment Method : " + paymentMethod);

        System.out.println();
        System.out.println("Processing payment...");

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        successful = true;

        System.out.println("Payment successful!");
        System.out.println("Payment ID: " + paymentId);

        System.out.println("----------------------------------------------");

        return successful;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
