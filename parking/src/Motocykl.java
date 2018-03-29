import java.io.IOException;
import java.time.LocalDateTime;

public class Motocykl extends Pojazd {

    protected boolean trzyKola;

    public Motocykl(Double cena, LocalDateTime dataIn, LocalDateTime dataOut, String nrRejString, Integer x, Integer y, Integer rodzajPojazdu, boolean trzyKola) {
        super(cena, dataIn, dataOut, nrRejString, x, y, rodzajPojazdu);
        this.trzyKola = trzyKola;
    }

    public Motocykl(LocalDateTime dataOut) {
        super(dataOut);
    }

    @Override
    public void parkowanie() {
        try {
            wp.setRejestrParkowanM(cena, dataIn, dataOut, x, y, rodzajPojazdu, nrRejString, trzyKola);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
            System.out.println(e.toString());
        }
    }

    @Override
    public void wyjazd(int index) {
        try {
            wp.setRejestrParkowanWyjazdM(index, cena, dataIn, dataOut, x, y, rodzajPojazdu, nrRejString, trzyKola);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
            System.out.println(e.toString());
        }
    }

}
