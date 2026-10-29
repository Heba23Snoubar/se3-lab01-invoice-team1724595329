package s1.lab2;

<<<<<<< HEAD
public class Invoice {

    public String number  ;
    public String Email ;

    public String number() {
        return number;
    }

    public String customerEmail() {
        return number;
    }
}
=======
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private String number;
    private String customerEmail;
    private List<InvoiceItem> items = new ArrayList<>();

    public Invoice(String number, String customerEmail) {
        this.number = number;
        this.customerEmail = customerEmail;
    }

    public void addItem(String name, double price, int qty) {
        items.add(new InvoiceItem(name, price, qty));
    }

    public List<InvoiceItem> getItems()  { return items; }
    public String getNumber()            { return number; }
    public String getCustomerEmail()     { return customerEmail; }
}
>>>>>>> main
