import java.time.LocalDateTime;
import java.util.ArrayList;

public class Wrapper {

    private ArrayList<Kontener> rejestrPojazdow;
    private static ArrayList<Kontener> rejestrParkowan;

    public Wrapper() {
        this.rejestrPojazdow = new ArrayList<>();
        this.rejestrParkowan = new ArrayList<>();
    }

    public void setRejestrParkowan(double cena, LocalDateTime dataIn, LocalDateTime dataOut, Integer x, Integer y, Integer rodzajPojazdu, String nrRejString) {
        Kontener k = new Kontener();
        k.setCena(cena);
        k.setDataIn(dataIn);
        k.setDataOut(dataOut);
        k.setX(x);
        k.setY(y);
        k.setRodzajPojazdu(rodzajPojazdu);
        k.setNrRejString(nrRejString);
        this.rejestrParkowan.add(k);
    }

    public static ArrayList<Kontener> getRejestrParkowan() {
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
