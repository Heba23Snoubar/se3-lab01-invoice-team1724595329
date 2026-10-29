package s1.lab2;

public class InvoiceService {
    private final InvoiceCalculator calculator;
    private final InvoiceFormatter formatter;
    private final InvoiceStorage storage;
    private final InvoiceSender sender;

    public InvoiceService(InvoiceCalculator calculator, InvoiceFormatter formatter,
                          InvoiceStorage storage, InvoiceSender sender) {
        this.calculator = calculator;
        this.formatter = formatter;
        this.storage = storage;
        this.sender = sender;
    }

    public double calculateTotal(Invoice invoice) {
        return calculator.calculateTotal(invoice);
    }

    public String printInvoice(Invoice invoice) {
        double total = calculator.calculateTotal(invoice);
        String text = formatter.formatInvoice(invoice, total);
        System.out.println(text);
        return text;
    }

    public void save(Invoice invoice) {
        double total = calculator.calculateTotal(invoice);
        storage.save(formatter.formatRecord(invoice, total));
    }


    public String send(Invoice invoice) {
        String text = printInvoice(invoice);
        return sender.send(invoice, text);
    }
}