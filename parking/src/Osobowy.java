import java.io.IOException;
import java.time.LocalDateTime;

public class Osobowy extends Pojazd {
    protected Double mycie;

    public Osobowy(Double cena, LocalDateTime dataIn, LocalDateTime dataOut, String nrRejString, Integer x, Integer y, Integer rodzajPojazdu, Double mycie) {
        super(cena, dataIn, dataOut, nrRejString, x, y, rodzajPojazdu);
        this.mycie = mycie;
    }

    @Override
    public void parkowanie() {
        try {
            wp.setRejestrParkowanO(cena, dataIn, dataOut, x, y, rodzajPojazdu, nrRejString, mycie);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
            System.out.println(e.toString());
        }
    }

    @Override
    public void wyjazd(int index) {
        try {
            wp.setRejestrParkowanWyjazdO(index, cena, dataIn, dataOut, x, y, rodzajPojazdu, nrRejString, mycie);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
            System.out.println(e.toString());
        }
    }
}
