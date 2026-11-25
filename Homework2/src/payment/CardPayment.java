
package src.payment;

public class CardPayment implements src.payment.PaymentStrategy {
    private static final double FEE_RATE = 0.02; // 2% card fee

    @Override
    public double pay(double amount) {
        double total = amount * (1 + FEE_RATE);
        System.out.printf(
                "Card payment processed. Base amount: %.2f, total with fee: %.2f%n",
                amount, total
        );
        return total;
    }
}