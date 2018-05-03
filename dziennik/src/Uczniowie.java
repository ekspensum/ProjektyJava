import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Uczniowie implements Serializable {
    private String imieUcznia, nazwiskoUcznia, loginUcznia;
    private char [] hasloUcznia;
    private Date dataUrodzenia;
    private Integer idUcznia;
    private Integer idKlasy;
    private static ArrayList<Uczniowie> uczniowie = new ArrayList<>();
    private FileOutputStream fosU;
    private ObjectOutputStream oosU;
    private FileInputStream fisU;
    private ObjectInputStream oisU;

    public Uczniowie(Integer idUcznia, Integer idKlasy, String imieUcznia, String nazwiskoUcznia, String loginUcznia, char [] hasloUcznia, Date dataUrodzenia) {
        this.idUcznia = idUcznia;
        this.idKlasy = idKlasy;
        this.imieUcznia = imieUcznia;
        this.nazwiskoUcznia = nazwiskoUcznia;
        this.loginUcznia = loginUcznia;
        this.hasloUcznia = hasloUcznia;
        this.dataUrodzenia = dataUrodzenia;
    }

    public Uczniowie() {
        try {
            przypiszListeUczniow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getIdUcznia() {
        return idUcznia;
    }

    public Integer getIdKlasy() {
        return idKlasy;
    }

    public String getImieUcznia() {
        return imieUcznia;
    }

    public String getNazwiskoUcznia() {
        return nazwiskoUcznia;
    }

    public String getLoginUcznia() {
        return loginUcznia;
    }

    public char[] getHasloUcznia() {
        return hasloUcznia;
    }

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void dodajUcznia(Integer idUcznia, Integer idKlasy, String imie, String nazwisko, String login, char [] haslo, Date dataUrodzenia) throws IOException {
        uczniowie.add(new Uczniowie(idUcznia, idKlasy, imie, nazwisko, login, haslo, dataUrodzenia));
        try {
            fosU = new FileOutputStream("uczniowie.dz");
            oosU = new ObjectOutputStream(fosU);
            oosU.writeObject(uczniowie);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oosU.close();
            fosU.close();
        }
    }

    public void przypiszListeUczniow() throws IOException {
        try {
            fisU = new FileInputStream("uczniowie.dz");
            oisU = new ObjectInputStream(fisU);
            uczniowie = (ArrayList<Uczniowie>) oisU.readObject();
            fisU.close();
            oisU.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Uczniowie> getUczniowie() {
        return uczniowie;
    }
}
