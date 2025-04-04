package ud2.examples.copyshop;

import java.util.ArrayList;
import java.util.List;

public class CustomerThread extends Thread {
    private List<Document> documents;
    private String name;
    private CopyShop shop;

    public CustomerThread(String name, CopyShop shop) {
        this.name = name;
        super.setName(name);

        this.documents = new ArrayList<>();
        this.shop = shop;
    }

    public List<Document> getDocuments() {
        return documents;
    }
    public void addDocument(String title, int pages) {
        documents.add(new Document(title, pages));
    }

    @Override
    public String toString() {
        return "CustomerThread{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void run() {
        try {
            System.out.printf("%s vol imprimir documents\n", this.name);
            Printer printer = shop.getFreePrinter();
            System.out.printf("%s ha obtingut l'impresora: %s\n", this.name, printer.getModel());
            for (Document d : documents) {
                printer.printDocument(d);
            }
            System.out.printf("%s ha acabat d'imprimir documents\n", this.name);
            shop.releasePrinter(printer);
            System.out.printf("%s ha alliberat l'impresora: %s\n", this.name, printer.getModel());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}