import java.util.ArrayList;

public class Przedmioty {

    private String nazwaPrzedmiotu;
    private ArrayList<Przedmioty> przedmiotyWszystkie = new ArrayList<>();

    public String getNazwaPrzedmiotu() {
        return nazwaPrzedmiotu;
    }

    public void setNazwaPrzedmiotu(String nazwaPrzedmiotu) {
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
    }

    public ArrayList<Przedmioty> getPrzedmiotyWszystkie() {
        return przedmiotyWszystkie;
    }

    public void setPrzedmiotyWszystkie(ArrayList<Przedmioty> przedmiotyWszystkie) {
        this.przedmiotyWszystkie = przedmiotyWszystkie;
    }
}
