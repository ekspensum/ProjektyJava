import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

//Baza wpisów do dziennika
public class BazaWpisow {

    private ArrayList<Nauczyciele> nauczyciele = new ArrayList<>();
    private ArrayList<Klasa<Uczniowie, Przedmioty>> klasy = new ArrayList<>();
    private ArrayList<Dziennik> wpisyDoDziennika = new ArrayList<>();
    private static ArrayList<Uczniowie> uczniowieWszyscy = new ArrayList<>();
    private Uczniowie daneUcznia;
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

    public void dodajUcznia(String imie, String nazwisko, String login, String haslo, Date dataUrodzenia){
        uczniowieWszyscy.add(new Uczniowie(imie, nazwisko, login, haslo, dataUrodzenia));
    }

    public ArrayList<Uczniowie> getUczniowieWszyscy() {
        return uczniowieWszyscy;
    }
}
