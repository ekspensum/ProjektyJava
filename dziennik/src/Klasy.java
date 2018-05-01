import java.io.*;
import java.util.ArrayList;

public class Klasy<U, P> implements Serializable {

    private U uczniowieObiekt;
    private P przedmiotyObiekt;
    private Integer idKlasy;
    private String nazwaKlasy;
    private static ArrayList<Klasy<Uczniowie, Przedmioty>> klasy = new ArrayList<>();
    private FileOutputStream fosK;
    private ObjectOutputStream oosK;
    private FileInputStream fisK;
    private ObjectInputStream oisK;

    public Klasy(Integer idKlasy, String nazwaKlasy) {
        this.idKlasy = idKlasy;
        this.nazwaKlasy = nazwaKlasy;
    }

    public Klasy(U uczniowieObiekt, P przedmiotyObiekt) {
        this.uczniowieObiekt = uczniowieObiekt;
        this.przedmiotyObiekt = przedmiotyObiekt;
        try {
            przypiszListeKlas();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("działa konstruktor Klasy");
    }

    public Klasy() {

    }

    public U getUczniowieObiekt() {
        return uczniowieObiekt;
    }

    public P getPrzedmiotyObiekt() {
        return przedmiotyObiekt;
    }

    public Integer getIdKlasy() {
        return idKlasy;
    }

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }

    public ArrayList<Klasy<Uczniowie, Przedmioty>> getKlasy() {
        return klasy;
    }

    public void dodajKlase(Integer idKlasy, String nazwaKlasy) throws IOException {
        klasy.add(new Klasy<Uczniowie, Przedmioty>(idKlasy, nazwaKlasy));
        try {
            fosK = new FileOutputStream("klasy.dz");
            oosK = new ObjectOutputStream(fosK);
            oosK.writeObject(klasy);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oosK.close();
            fosK.close();
        }
    }

    public void przypiszListeKlas() throws IOException {
        try {
            fisK = new FileInputStream("klasy.dz");
            oisK = new ObjectInputStream(fisK);
            klasy = (ArrayList<Klasy<Uczniowie, Przedmioty>>) oisK.readObject();
            fisK.close();
            oisK.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
