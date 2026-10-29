package s1.lab2;

import java.util.Locale;

public class InvoiceFormatter {

    public String formatInvoice(Invoice invoice, double total) {
        StringBuilder sb = new StringBuilder();
        sb.append("INVOICE ").append(invoice.getNumber()).append("\n");
        for (InvoiceItem i : invoice.getItems()) {
            sb.append(String.format(Locale.US, "%s x%d = %.2f",
                    i.getName(), i.getQty(), i.getPrice() * i.getQty())).append("\n");
        }
        sb.append(String.format(Locale.US, "TOTAL: %.2f", total));
        return sb.toString();
    }

    public String formatRecord(Invoice invoice, double total) {
        return String.format(Locale.US, "%s;%s;%.2f",
                invoice.getNumber(), invoice.getCustomerEmail(), total);
    }
}