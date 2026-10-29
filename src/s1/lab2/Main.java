package s1.lab2;

public class Main {

    public static void main(String[] args) {
        IInvoiceStorage repository = new FileStorage();
        InvoiceService service = new InvoiceService(
                new InvoiceCalculator(),
                new InvoiceFormatter(),
                repository,
                new EmailSender());

        Invoice small = new Invoice("INV-1un", "a@uni.edu");
        small.addItem("Book", 100.0, 2);
        small.addItem("Pen", 5.0, 10);
        service.issue(small);

        System.out.println("\n-- subtotal > 1000 (5% discount) --");
        Invoice big = new Invoice("INV-2", "b@uni.edu");
        big.addItem("Laptop", 1200.0, 1);


        service.issue(big);

        System.out.println("\nSTORED: " + repository.getRows());
    }
}