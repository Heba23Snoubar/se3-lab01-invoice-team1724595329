package src.payment;

public class CashOnDelivery implements src.payment.PaymentStrategy {
    private static final double DELIVERY_FEE = 10.0;

    @Override
    public double pay(double amount) {
        double total = amount + DELIVERY_FEE;
        System.out.printf(
                "Cash on delivery selected. Base amount: %.2f, delivery fee: %.2f, total: %.2f%n",
                amount, DELIVERY_FEE, total
        );
        return total;
    }
}