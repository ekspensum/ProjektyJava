import java.io.IOException;
import java.time.LocalDateTime;

public class Dostawczy extends Pojazd {


    public Dostawczy(Double cena, LocalDateTime dataIn, LocalDateTime dataOut, String nrRejString, Integer x, Integer y, Integer rodzajPojazdu) {
        super(cena, dataIn, dataOut, nrRejString, x, y, rodzajPojazdu);
    }

    @Override
    public void parkowanie() {
        try {
            wp.setRejestrParkowanD(cena, dataIn, dataOut, x, y, rodzajPojazdu, nrRejString);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
            System.out.println(e.toString());
        }
    }

    @Override
    public void wyjazd() {

    }
}
