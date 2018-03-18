import java.io.IOException;

public class Motocykl extends Pojazd {

    public Motocykl() {
        super();
    }

    @Override
    public void parkowanie() {
        try {
            wp.setRejestrParkowan(cena, dataIn, dataOut, x, y, rodzajPojazdu, nrRejString);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
            System.out.println(e.toString());
        }
    }

    @Override
    public void wyjazd() {

    }

}
