import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

//Baza wpisów do dziennika
public class BazaWpisow {

    private Dziennik<Nauczyciele, Klasy<Uczniowie, Przedmioty>> dziennik = new Dziennik<>(new Nauczyciele(), new Klasy<>(new Uczniowie(), new Przedmioty()));
    private static ArrayList<Dziennik<Nauczyciele, Klasy<Uczniowie, Przedmioty>>> wpisyDoDziennika = new ArrayList<>();
//    private FileOutputStream fosN, fosU;
//    private ObjectOutputStream oosN, oosU;
//    private FileInputStream fisN, fisU;
//    private ObjectInputStream oisN, oisU;


    public BazaWpisow() {


    }


    public void setWpisyDoDziennika(){
        dziennik.getNauczycieleObiekt().getNauczyciele().get(0).getIdNauczyciela();
        dziennik.getKlasyObiekt().getKlasy().get(0).getUczniowieObiekt().getUczniowie().get(0).getIdUcznia();



        wpisyDoDziennika.add(dziennik);
    }

}
