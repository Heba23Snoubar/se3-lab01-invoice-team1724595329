package s1.lab2;

import java.util.ArrayList;
import java.util.List;

public class FileStorage implements InvoiceStorage {
    public static final List<String> FILES = new ArrayList<>();

    @Override
    public void save(String record) {
        FILES.add(record);
    }
}