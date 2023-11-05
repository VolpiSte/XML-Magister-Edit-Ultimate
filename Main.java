import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {


        // Esempio di aggiunta di dati
        Regione t1 = new Regione("Toscana", new Provincia("Lucca", new Comune("Lucca", 0, 8, new Classe("Ste1"))));
        Regione t2 = new Regione("Toscana", new Provincia("Massa Carrara", new Comune("Massa", 2, 0, new Classe("Ste2"))));
        Regione t3 = new Regione("Toscana", new Provincia("Pisa", new Comune("Pisa", 0, 4, new Classe("Ste3"))));
        Regione t4 = new Regione("Toscana", new Provincia("Pistoia", new Comune("Pistoia", 5, 7, new Classe("Ste4"))));

        ArrayList<Regione> sismaApuane = new ArrayList<>();
        sismaApuane.add(t1);
        sismaApuane.add(t2);
        sismaApuane.add(t3);
        sismaApuane.add(t4);


        // Esempio di salvataggio dei dati in XML
        saveDataToXML(sismaApuane, "sismaApuane1.xml");
    
        // Stampa tutti i dati
        System.out.println(sismaApuane);
        System.out.println("--------------------------------------------------");

        // Esempio di stampa dei dati
        XMLDecoder decoder = new XMLDecoder(new FileInputStream("sismaApuane1.xml"));
        ArrayList<Regione> data = new ArrayList<>();
        data = (ArrayList<Regione>)decoder.readObject();
        decoder.close();
        for(int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }
    }       

    public static ArrayList<Regione> loadDataFromXML(String fileName) {
        ArrayList<Regione> data = new ArrayList<>();
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(fileName))) {
            Object obj = decoder.readObject();
            if (obj instanceof ArrayList<?>) {
                data = (ArrayList<Regione>) obj;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void saveDataToXML(ArrayList<Regione> data, String fileName) {
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(fileName))) {
            encoder.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}