import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Nauczyciele implements Serializable {
    private String imieNauczyciela, nazwiskoNauczyciela, loginNauczyciela, haslowNauczyciela;
    private Date dataZatrudnienia;
    private Integer idNauczyciela;
    private static ArrayList<Nauczyciele> nauczyciele = new ArrayList<>();
    private FileOutputStream fosN;
    private ObjectOutputStream oosN;
    private FileInputStream fisN;
    private ObjectInputStream oisN;

    public Nauczyciele(Integer idNauczyciela, String imieNauczyciela, String nazwiskoNauczyciela, String loginNauczyciela, String haslowNauczyciela, Date dataZatrudnienia) {
        this.idNauczyciela = idNauczyciela;
        this.imieNauczyciela = imieNauczyciela;
        this.nazwiskoNauczyciela = nazwiskoNauczyciela;
        this.loginNauczyciela = loginNauczyciela;
        this.haslowNauczyciela = haslowNauczyciela;
        this.dataZatrudnienia = dataZatrudnienia;
    }

    public Nauczyciele() {
        try {
            przypiszListeNauczycieli();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getIdNauczyciela() {
        return idNauczyciela;
    }

    public String getImieNauczyciela() {
        return imieNauczyciela;
    }

    public String getNazwiskoNauczyciela() {
        return nazwiskoNauczyciela;
    }

    public String getLoginNauczyciela() {
        return loginNauczyciela;
    }

    public Date getDataZatrudnienia() {
        return dataZatrudnienia;
    }

    public void dodajNauczyciela(Integer idNauczyciela, String imieNauczyciela, String nazwiskoNauczyciela, String loginNauczyciela, String haslowNauczyciela, Date dataZatrudnienia)throws IOException {
        nauczyciele.add(new Nauczyciele(idNauczyciela, imieNauczyciela, nazwiskoNauczyciela, loginNauczyciela, haslowNauczyciela, dataZatrudnienia));
        try {
            fosN = new FileOutputStream("nauczyciele.dz");
            oosN = new ObjectOutputStream(fosN);
            oosN.writeObject(nauczyciele);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oosN.close();
            fosN.close();
        }
    }

    public ArrayList<Nauczyciele> getNauczyciele() {
        return nauczyciele;
    }

    public void przypiszListeNauczycieli() throws IOException {
        try {
            fisN = new FileInputStream("nauczyciele.dz");
            oisN = new ObjectInputStream(fisN);
            nauczyciele = (ArrayList<Nauczyciele>) oisN.readObject();
            fisN.close();
            oisN.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
