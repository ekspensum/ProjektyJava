import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.lang.System.out;

public class Wrapper implements Serializable {

    private ArrayList<Kontener> rejestrPojazdow;
    private static ArrayList<Kontener> rejestrParkowan;

    public Wrapper() {
        this.rejestrPojazdow = new ArrayList<>();
        this.rejestrParkowan = new ArrayList<>();
    }

    public void setRejestrParkowan(double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString) throws IOException {
        Kontener k = new Kontener();
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        this.rejestrParkowan.add(k);
        FileOutputStream fos = new FileOutputStream("parkowanie.p");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(rejestrParkowan);
        oos.close();
    }

    public void setRejestrParkowanM(double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, boolean trzyKola) throws IOException {
        Kontener k = new Kontener();
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setTrzyKola(trzyKola);
        this.rejestrParkowan.add(k);
        FileOutputStream fos = new FileOutputStream("parkowanie.p");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(rejestrParkowan);
        oos.close();
    }

    public static ArrayList<Kontener> getRejestrParkowan() throws IOException {
        FileInputStream fis = new FileInputStream("parkowanie.p");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            rejestrParkowan = (ArrayList<Kontener>) ois.readObject();
        } catch (ClassNotFoundException c) {
            System.out.println(c.getException());
        }
        return rejestrParkowan;
    }

    public void setRejestrPojazdow(String nrRejString, Integer rodzajPojazdu) {
        Kontener k = new Kontener();
        k.setNrRejString(nrRejString);
        k.setRodzajPojazdu(rodzajPojazdu);
        this.rejestrPojazdow.add(k);
    }

    public ArrayList<Kontener> getRejestrPojazdow() {
        return rejestrPojazdow;
    }
}
