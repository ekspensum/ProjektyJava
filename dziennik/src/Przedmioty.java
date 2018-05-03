import java.io.*;
import java.util.ArrayList;

public class Przedmioty implements Serializable {

    private String nazwaPrzedmiotu;
    private Integer idPrzedmiotu;
    private Integer idKlasy;
    private static ArrayList<Przedmioty> przedmioty = new ArrayList<>();
    private FileOutputStream fosP;
    private ObjectOutputStream oosP;
    private FileInputStream fisP;
    private ObjectInputStream oisP;

    public Przedmioty(String nazwaPrzedmiotu, Integer idPrzedmiotu, Integer idKlasy) {
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
        this.idPrzedmiotu = idPrzedmiotu;
        this.idKlasy = idKlasy;
    }

    public Przedmioty() {
        try {
            przypiszListePrzedmiotow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNazwaPrzedmiotu() {
        return nazwaPrzedmiotu;
    }

    public Integer getIdPrzedmiotu() {
        return idPrzedmiotu;
    }

    public Integer getIdKlasy() {
        return idKlasy;
    }

    public ArrayList<Przedmioty> getPrzedmioty() {
        return przedmioty;
    }

    public void dodajPrzedmiot(String nazwaPrzedmiotu, Integer idPrzedmiotu, Integer idKlasy) throws IOException {
        przedmioty.add(new Przedmioty(nazwaPrzedmiotu, idPrzedmiotu, idKlasy));
        try {
            fosP = new FileOutputStream("przedmioty.dz");
            oosP = new ObjectOutputStream(fosP);
            oosP.writeObject(przedmioty);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oosP.close();
            fosP.close();
        }
    }

    public void przypiszListePrzedmiotow() throws IOException {
        try {
            fisP = new FileInputStream("przedmioty.dz");
            oisP = new ObjectInputStream(fisP);
            przedmioty = (ArrayList<Przedmioty>) oisP.readObject();
            fisP.close();
            oisP.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
