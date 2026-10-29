package s1.lab2;

import java.util.ArrayList;
import java.util.List;
 
public class DatabaseStorage implements IInvoiceStorage{


    private static   final List<String> DatabaseRows =new ArrayList<>();

    @Override
    public void Save(Invoice invoice, double total) {

        DatabaseRows.add((invoice.getNumber() +";" + invoice.getCustomerEmail() +";" +total));
    }
   public    List<String> getRows(){
        return  DatabaseRows;
    }

}
 