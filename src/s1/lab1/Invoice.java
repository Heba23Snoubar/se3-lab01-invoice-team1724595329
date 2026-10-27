
package s1.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Invoice {
    private final String number;
    private final String customerEmail;
    private final List<InvoiceItem> items;

    public static final List<String> DATABASE = new ArrayList<>();
    public static final List<String> FILES = new ArrayList<>();

    public Invoice(String number, String customerEmail) {
        this.number = number;
        this.customerEmail = customerEmail;
        this.items = new ArrayList<>();
    }

    public void addItem(String name, double price, int qty) {
        items.add(new InvoiceItem(name, price, qty));
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public double calculateTotal() {
        double total = 0;

        for (InvoiceItem item : items) {
            total += item.getPrice() * item.getQty();
        }

        if (total > 1000) {
            total *= 0.95;
        }

        total *= 1.15;
        return total;
    }

    public String printInvoice() {
        StringBuilder text = new StringBuilder();

        text.append("INVOICE ").append(number).append("\n");

        for (InvoiceItem item : items) {
            double itemTotal = item.getPrice() * item.getQty();

            text.append(item.getName())
                    .append(" x")
                    .append(item.getQty())
                    .append(" = ")
                    .append(String.format(Locale.US, "%.2f", itemTotal))
                    .append("\n");
        }

        text.append("TOTAL: ")
                .append(String.format(Locale.US, "%.2f", calculateTotal()));

        String invoiceText = text.toString();

        System.out.println(invoiceText);

        return invoiceText;
    }

    public void saveToDatabase(String type) {
        String record = number + ";" + customerEmail + ";"
                + String.format(Locale.US, "%.2f", calculateTotal());

        if ("DATABASE".equals(type)) {
            DATABASE.add(record);
        } else {
            FILES.add(record);
        }
    }

    public String sendEmail() {
        String invoiceText = printInvoice();

        System.out.println("Sending email...");

        return "To: " + customerEmail
                + "\nSubject: Invoice " + number
                + "\n" + invoiceText;
    }

    public String sendwhatsapp() {
        String invoiceText = printInvoice();

        System.out.println("Sending sendwhatsapp...");

        return "To: sendwhatsapp\n" + invoiceText;
    }
}
