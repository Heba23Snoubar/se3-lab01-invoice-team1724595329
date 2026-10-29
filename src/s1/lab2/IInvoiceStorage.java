package s1.lab2;


import java.util.List;

public interface IInvoiceStorage {

    public  void Save(Invoice invoice ,double total);


    public List<String> getRows();

}
