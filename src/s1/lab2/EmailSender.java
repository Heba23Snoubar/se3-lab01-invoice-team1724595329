package s1.lab2;

public class EmailSender implements  IInvoiceSender{

    @Override
    public String Send(String numberOrder, String customerEmail) {

        String message = "To: " + customerEmail + "\nSubject: Invoice " + numberOrder + "\n\n";
        System.out.println("Sending Email  ...");

        return message;
    }

}
