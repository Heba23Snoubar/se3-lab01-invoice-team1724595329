package s1.lab2;

<<<<<<< HEAD
public class WhatsAppSender implements  IInvoiceSender {


    @Override
    public String Send(String numberOrder, String customerEmail) {

        String message = "To: " + customerEmail + "\nSubject: Invoice " + numberOrder + "\n\n";
        System.out.println("Sending send whatsapp ...");

        return message;
    }

=======
public class WhatsAppSender implements InvoiceSender {

    @Override
    public String send(Invoice invoice, String invoiceText) {
        String msg = "To: sendwhatsapp"
                + "\nSubject: Invoice " + invoice.getNumber()
                + "\n" + invoiceText;
        System.out.println("Sending sendwhatsapp...");
        return msg;
    }
>>>>>>> main
}