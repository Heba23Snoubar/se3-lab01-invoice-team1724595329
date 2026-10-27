
package s1.lab1;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        // Invoice 1: Total should be 230.00
        Invoice invoice1 = new Invoice("INV-1", "lina@uni.edu");
        invoice1.addItem("Book", 100, 2);

        invoice1.printInvoice();
        invoice1.saveToDatabase("DATABASE");

        System.out.println("DATABASE: " + Invoice.DATABASE);
        invoice1.sendEmail();
        invoice1.sendwhatsapp();
        System.out.printf(
                Locale.US,
                "Final total: %.2f%n",
                invoice1.calculateTotal()
        );

        System.out.println("--------------------");

        // Invoice 2: Total should be 1311.00
        Invoice invoice2 = new Invoice("INV-2", "ahmad@uni.edu");
        invoice2.addItem("Laptop", 1200, 1);

        invoice2.printInvoice();
        invoice2.saveToDatabase("FILES");

        System.out.println("FILES: " + Invoice.FILES);
        invoice2.sendEmail();
        invoice2.sendwhatsapp();
        System.out.printf(
                Locale.US,
                "Final total: %.2f%n",
                invoice2.calculateTotal()
        );

        System.out.println("--------------------");

        // Invoice 3: Total should be 1150.00 (no discount)
        Invoice invoice3 = new Invoice("INV-3", "sara@uni.edu");
        invoice3.addItem("Monitor", 500, 2);

        invoice3.printInvoice();
        invoice3.saveToDatabase("DATABASE");

        System.out.println("DATABASE: " + Invoice.DATABASE);
        invoice3.sendEmail();
        invoice3.sendwhatsapp();
        System.out.printf(
                Locale.US,
                "Final total: %.2f%n",
                invoice3.calculateTotal()
        );
    }
}
