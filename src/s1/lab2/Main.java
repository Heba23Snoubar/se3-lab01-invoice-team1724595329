package s1.lab2;

import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        InvoiceCalculator calculator = new InvoiceCalculator();
        InvoiceFormatter formatter = new InvoiceFormatter();

        InvoiceStorage database = new DatabaseStorage();
        InvoiceStorage files = new FileStorage();

        InvoiceSender email = new EmailSender();
        InvoiceSender whatsapp = new WhatsAppSender();

        Invoice a = new Invoice("INV-1", "lina@uni.edu");
        a.addItem("Book", 100, 2);
        issue(a, new InvoiceService(calculator, formatter, database, email),
                new InvoiceService(calculator, formatter, database, whatsapp),
                "DATABASE", DatabaseStorage.DATABASE);
        System.out.println("--------------------");

        Invoice b = new Invoice("INV-2", "ahmad@uni.edu");
        b.addItem("Laptop", 1200, 1);
        issue(b, new InvoiceService(calculator, formatter, files, email),
                new InvoiceService(calculator, formatter, files, whatsapp),
                "FILES", FileStorage.FILES);
        System.out.println("--------------------");

        Invoice c = new Invoice("INV-3", "sara@uni.edu");
        c.addItem("Monitor", 500, 2);
        issue(c, new InvoiceService(calculator, formatter, database, email),
                new InvoiceService(calculator, formatter, database, whatsapp),
                "DATABASE", DatabaseStorage.DATABASE);
    }

    private static void issue(Invoice invoice, InvoiceService viaEmail,
                              InvoiceService viaWhatsapp,
                              String label, List<String> content) {
        viaEmail.printInvoice(invoice);
        viaEmail.save(invoice);
        System.out.println(label + ": " + content);
        viaEmail.send(invoice);
        viaWhatsapp.send(invoice);
        System.out.println(String.format(Locale.US, "Final total: %.2f",
                viaEmail.calculateTotal(invoice)));
    }
}