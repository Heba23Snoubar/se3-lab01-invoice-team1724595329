package s1.lab2;

<<<<<<< HEAD

import java.util.ArrayList;
import java.util.List;

public class FileStorage implements IInvoiceStorage{

    private static final List<String> Filerows =new ArrayList<>();

    @Override
    public void Save(Invoice invoice, double total) {

        Filerows.add((invoice.number() +";" + invoice.customerEmail() +";" +total));
    }
    List<String> getRows(){
        return  Filerows;
    }


}
=======
import java.util.ArrayList;
import java.util.List;

public class FileStorage implements InvoiceStorage {
    public static final List<String> FILES = new ArrayList<>();

    @Override
    public void save(String record) {
        FILES.add(record);
    }
}
>>>>>>> main
