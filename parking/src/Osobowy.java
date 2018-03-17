
public class Osobowy extends Pojazd {

    public Osobowy() {
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
