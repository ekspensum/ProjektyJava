import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Wrapper implements Serializable {

    private static ArrayList<Kontener> rejestrPojazdow = new ArrayList<>();
    private static ArrayList<Kontener> rejestrParkowan = new ArrayList<>();
    private FileOutputStream fos, fosPoj;
    private ObjectOutputStream oos, oosPoj;
    private Kontener k, kPoj;

    public Wrapper() {

    }

    public void setRejestrParkowanM(Double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, boolean trzyKola) throws IOException {
        k = new Kontener();
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setTrzyKola(trzyKola);
        rejestrParkowan.add(k);
        zapiszDoPliku(rejestrParkowan);
    }

    public void setRejestrParkowanO(Double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, Double mycie) throws IOException {
        k = new Kontener();
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setMycie(mycie);
        rejestrParkowan.add(k);
        zapiszDoPliku(rejestrParkowan);
    }

    public void setRejestrParkowanD(Double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, Double chlodnia) throws IOException {
        k = new Kontener();
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setChlodnia(chlodnia);
        rejestrParkowan.add(k);
        zapiszDoPliku(rejestrParkowan);
    }

    public void setRejestrParkowanWyjazdM(Integer index, Double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, boolean trzyKola) throws IOException {
        k = new Kontener();
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setTrzyKola(trzyKola);
        rejestrParkowan.set(index, k);
        zapiszDoPliku(rejestrParkowan);
    }

    public void setRejestrParkowanWyjazdO(Integer index, Double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, Double mycie) throws IOException {
        k = new Kontener();
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setMycie(mycie);
        rejestrParkowan.set(index, k);
        zapiszDoPliku(rejestrParkowan);
    }

    public void setRejestrParkowanWyjazdD(Integer index, Double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString, Double chlodnia) throws IOException {
        k = new Kontener();
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        k.setChlodnia(chlodnia);
        rejestrParkowan.set(index, k);
        zapiszDoPliku(rejestrParkowan);
    }

    public void przypiszRejestrParkowan() throws IOException {
        FileInputStream fis = new FileInputStream("parkowanie.p");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            rejestrParkowan = (ArrayList<Kontener>) ois.readObject();
            ois.close();
            fis.close();
        } catch (ClassNotFoundException c) {
            System.out.println(c.getException());
        }
    }

    public static ArrayList<Kontener> getRejestrParkowan() {
        return rejestrParkowan;
    }

    private void zapiszDoPliku(ArrayList<Kontener> rejestrParkowan) throws IOException {
        fos = new FileOutputStream("parkowanie.p");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(rejestrParkowan);
        oos.close();
        fos.close();
    }

    public void setRejestrPojazdow(String nrRejString, Integer rodzajPojazdu, Double cena) throws IOException {
        kPoj = new Kontener();
        kPoj.setNrRejString(nrRejString);
        kPoj.setRodzajPojazdu(rodzajPojazdu);
        kPoj.setCena(cena);
        rejestrPojazdow.add(kPoj);
        fosPoj = new FileOutputStream("pojazdy.p");
        oosPoj = new ObjectOutputStream(fosPoj);
        oosPoj.writeObject(rejestrPojazdow);
        oosPoj.close();
        fosPoj.close();
    }

    public static ArrayList<Kontener> getRejestrPojazdow() {
        return rejestrPojazdow;
    }

    public void przypiszRejestrPojazdow() throws IOException {
        FileInputStream fisPoj = new FileInputStream("pojazdy.p");
        ObjectInputStream oisPoj = new ObjectInputStream(fisPoj);
        try {
            rejestrPojazdow = (ArrayList<Kontener>) oisPoj.readObject();
        } catch (ClassNotFoundException cP) {
            System.out.println(cP.getException());
        }
    }
}
