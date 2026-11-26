package src.payment;

import java.util.Locale;

/**
 * Simulates a THIRD-PARTY PayPal library.
 *
 * !! Do NOT modify this class after it is written: treat it as an external
 * !! library whose source we do not control. Its interface
 * !! (makeTransaction(String, double)) is deliberately different from the
 * !! PaymentStrategy interface (pay(double)) that our system expects.
 * !! That mismatch is exactly the problem the Adapter pattern solves.
 */
public class ExternalPayPalLib {

    /**
     * @param currency ISO currency code, e.g. "USD"
     * @param value    amount to charge
     * @return the amount that was charged
     */
    public double makeTransaction(String currency, double value) {
        System.out.printf(Locale.US,
                "[PayPal lib] Transaction completed: %.2f %s%n", value, currency);
        return value;
    }
}