
public class Dostawczy extends Pojazd {

    public Dostawczy() {
        super();
    }

    @Override
    public void parkowanie() {
        wp.setRejestrParkowan(cena, dataIn, dataOut, x, y, rodzajPojazdu, nrRejString);
    }

    @Override
    public void wyjazd() {

    }
}
