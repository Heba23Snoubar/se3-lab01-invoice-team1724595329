package s1.lab2;

public class EmailSender implements InvoiceSender {

    @Override
    public String send(Invoice invoice, String invoiceText) {
        String msg = "To: " + invoice.getCustomerEmail()
                + "\nSubject: Invoice " + invoice.getNumber()
                + "\n" + invoiceText;
        System.out.println("Sending email...");
        return msg;
    }
}