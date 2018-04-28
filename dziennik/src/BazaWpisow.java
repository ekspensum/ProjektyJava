import java.time.LocalDateTime;
import java.util.ArrayList;

public class BazaWpisow {

    private ArrayList<Nauczyciel> nauczyciele = new ArrayList<>();
    private ArrayList<Klasa<Uczniowie, Przedmioty>> klasy = new ArrayList<>();
    private ArrayList<Dziennik> wpisyDoDziennika = new ArrayList<>();
    private Double [] oceny;
    private Dziennik dziennik;


    public BazaWpisow() {


    }


    public void setWpisyDoDziennika(){
        dziennik = new Dziennik();
        dziennik.setDataWpisu(LocalDateTime.now());
//        dziennik.setDataOceny();
//        dziennik.setIdUcznia();
//        dziennik.setImieUcznia();
//        dziennik.setNazwiskoUcznia();




        wpisyDoDziennika.add(dziennik);
    }


}
