import java.util.ArrayList;

public class Klasa<U, P> {

//    private U uczen;
//    private P przedmiot;

    private String nazwaKlasy;
    private ArrayList<Uczniowie> uczniowieKlasy = new ArrayList<>();
    private ArrayList<Przedmioty> przedmiotyKlasy = new ArrayList<>();

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }

    public ArrayList<Uczniowie> getUczniowieKlasy() {
        return uczniowieKlasy;
    }

    public ArrayList<Przedmioty> getPrzedmiotyKlasy() {
        return przedmiotyKlasy;
    }

    public void setNazwaKlasy(String nazwaKlasy) {
        this.nazwaKlasy = nazwaKlasy;
    }

    public void setUczniowieKlasy(ArrayList<Uczniowie> uczniowieKlasy) {
        this.uczniowieKlasy = uczniowieKlasy;
    }

    public void setPrzedmiotyKlasy(ArrayList<Przedmioty> przedmiotyKlasy) {
        this.przedmiotyKlasy = przedmiotyKlasy;
    }
}
