import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Wrapper implements Serializable {

    private ArrayList<Kontener> rejestrPojazdow;
    private static ArrayList<Kontener> rejestrParkowan = new ArrayList<>();
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private Kontener k;

    public Wrapper() {
        this.rejestrPojazdow = new ArrayList<>();
        k = new Kontener();
    }

    public void setRejestrParkowanM(double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, boolean trzyKola) throws IOException {
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setTrzyKola(trzyKola);
        rejestrParkowan.add(k);
        fos = new FileOutputStream("parkowanie.p");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(rejestrParkowan);
        oos.close();
    }

    public void setRejestrParkowanO(double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, Double mycie) throws IOException {
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setMycie(mycie);
        rejestrParkowan.add(k);
        fos = new FileOutputStream("parkowanie.p");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(rejestrParkowan);
        oos.close();
    }

    public void setRejestrParkowanD(double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString) throws IOException {
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        rejestrParkowan.add(k);
        fos = new FileOutputStream("parkowanie.p");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(rejestrParkowan);
        oos.close();
    }

    public void setRejestrParkowanWyjazdM(int index, double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, boolean trzyKola) throws IOException {
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setTrzyKola(trzyKola);
        rejestrParkowan.set(index, k);
        fos = new FileOutputStream("parkowanie.p");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(rejestrParkowan);
        oos.close();
    }

    public void setRejestrParkowanWyjazdO(int index, double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, Double mycie) throws IOException {
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setMycie(mycie);
        rejestrParkowan.set(index, k);
        fos = new FileOutputStream("parkowanie.p");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(rejestrParkowan);
        oos.close();
    }

    public void przypiszRejestrParkowan() throws IOException {
        FileInputStream fis = new FileInputStream("parkowanie.p");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            rejestrParkowan = (ArrayList<Kontener>) ois.readObject();
        } catch (ClassNotFoundException c) {
            System.out.println(c.getException());
        }
    }

    public static ArrayList<Kontener> getRejestrParkowan() {
        return rejestrParkowan;
    }

    public void setRejestrPojazdow(String nrRejString, Integer rodzajPojazdu) {
        k = new Kontener();
        k.setNrRejString(nrRejString);
        k.setRodzajPojazdu(rodzajPojazdu);
        this.rejestrPojazdow.add(k);
    }

    public ArrayList<Kontener> getRejestrPojazdow() {
        return rejestrPojazdow;
    }
}
