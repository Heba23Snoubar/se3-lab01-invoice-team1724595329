package s1.lab2;

public class WhatsAppSender implements InvoiceSender {

    @Override
    public String send(Invoice invoice, String invoiceText) {
        String msg = "To: sendwhatsapp"
                + "\nSubject: Invoice " + invoice.getNumber()
                + "\n" + invoiceText;
        System.out.println("Sending sendwhatsapp...");
        return msg;
    }
}