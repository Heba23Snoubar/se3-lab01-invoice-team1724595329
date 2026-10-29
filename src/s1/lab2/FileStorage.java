package s1.lab2;

import java.util.ArrayList;
import java.util.List;

public class FileStorage implements IInvoiceStorage{

    private static final List<String> Filerows =new ArrayList<>();

    @Override
    public void Save(Invoice invoice, double total) {

        Filerows.add((invoice.getNumber() +";" + invoice.getCustomerEmail() +";" +total));
    }
  public  List<String> getRows(){
        return  Filerows;
    }


}
