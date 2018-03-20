import java.io.IOException;

public class Motocykl extends Pojazd {

    protected boolean trzyKola;

    public Motocykl() {
        super();
    }

    public Motocykl(boolean trzyKola) {
        super();
        this.trzyKola = trzyKola;
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
    public void wyjazd() {

    }

}
