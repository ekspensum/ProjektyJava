import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

//Baza wpisów do dziennika
public class BazaWpisow implements Serializable {
    private KontenerBazy kontenerBazy;
    private static ArrayList<KontenerBazy> wpisyDoDziennika = new ArrayList<>();
    private FileOutputStream fosD;
    private ObjectOutputStream oosD;
    private FileInputStream fisD;
    private ObjectInputStream oisD;


    public BazaWpisow() {
        try {
            przypiszRejestrWpisow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWpisyDoDziennika(Integer idKlasy,
                                    String nazwaKlasy,
                                    Integer idNauczyciela,
                                    String imieNauczyciela,
                                    String nazwiskoNauczyciela,
                                    Integer idUcznia,
                                    String imieUcznia,
                                    String nazwiskoUcznia,
                                    String nazwaPrzedmiotu,
                                    LocalDateTime dataOperacji,
                                    Date dataOceny,
                                    Double ocena,
                                    String komentarzaNauczyciela) throws IOException {
        kontenerBazy = new KontenerBazy(idKlasy,
                                    nazwaKlasy,
                                    idNauczyciela,
                                    imieNauczyciela,
                                    nazwiskoNauczyciela,
                                    idUcznia,
                                    imieUcznia,
                                    nazwiskoUcznia,
                                    nazwaPrzedmiotu,
                                    dataOperacji,
                                    dataOceny,
                                    ocena,
                                    komentarzaNauczyciela);
        wpisyDoDziennika.add(kontenerBazy);
        try {
            fosD = new FileOutputStream("dziennik.dz");
            oosD = new ObjectOutputStream(fosD);
            oosD.writeObject(wpisyDoDziennika);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fosD.close();
            oosD.close();
        }
    }

    public void przypiszRejestrWpisow() throws IOException {
        try {
            fisD = new FileInputStream("dziennik.dz");
            oisD = new ObjectInputStream(fisD);
            wpisyDoDziennika = (ArrayList<KontenerBazy>) oisD.readObject();
            fisD.close();
            oisD.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<KontenerBazy> getWpisyDoDziennika() {
        return wpisyDoDziennika;
    }
}
