
public class Motocykl extends Pojazd {

    public Motocykl() {
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
