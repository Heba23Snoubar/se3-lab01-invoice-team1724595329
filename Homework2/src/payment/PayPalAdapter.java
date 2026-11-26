package src.payment;

import java.util.Objects;

/**
 * Adapter pattern: makes the incompatible ExternalPayPalLib usable as a
 * PaymentStrategy.
 *
 *   Target   : PaymentStrategy        (the interface our system expects)
 *   Adaptee  : ExternalPayPalLib      (the external library, cannot be changed)
 *   Adapter  : PayPalAdapter          (this class)
 *   Client   : Order / CheckoutFacade (only knows PaymentStrategy)
 *
 * pay(amount) is translated into makeTransaction("USD", amount).
 */
public class PayPalAdapter implements PaymentStrategy {

    private static final String CURRENCY = "USD";

    private final ExternalPayPalLib payPalLib;

    public PayPalAdapter() {
        this(new ExternalPayPalLib());
    }

    public PayPalAdapter(ExternalPayPalLib payPalLib) {
        this.payPalLib = Objects.requireNonNull(payPalLib, "payPalLib must not be null");
    }

    @Override
    public double pay(double amount) {
        // Interface conversion: pay(amount) -> makeTransaction("USD", amount)
        return payPalLib.makeTransaction(CURRENCY, amount);
    }
}