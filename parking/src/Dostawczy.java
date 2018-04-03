import java.io.IOException;
import java.time.LocalDateTime;

public class Dostawczy extends Pojazd {
    protected Double chlodnia;


    public Dostawczy(Double cena, LocalDateTime dataIn, LocalDateTime dataOut, String nrRejString, Integer x, Integer y, Integer rodzajPojazdu, Double chlodnia) {
        super(cena, dataIn, dataOut, nrRejString, x, y, rodzajPojazdu);
        this.chlodnia = chlodnia;
    }

    @Override
    public void parkowanie() {
        try {
            wp.setRejestrParkowanD(cena, dataIn, dataOut, x, y, rodzajPojazdu, nrRejString, chlodnia);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
            System.out.println(e.toString());
        }
    }

    @Override
    public void wyjazd(int index) {
        try {
            wp.setRejestrParkowanWyjazdD(index, cena, dataIn, dataOut, x, y, rodzajPojazdu, nrRejString, chlodnia);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
            System.out.println(e.toString());
        }
    }
}
