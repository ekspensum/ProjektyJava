import java.time.LocalDateTime;


public abstract class Pojazd {
    protected Double cena;
    protected LocalDateTime dataIn, dataOut;
    protected String nrRejString;
    protected Integer x, y;
    protected Integer rodzajPojazdu;
    protected Wrapper wp;

    public Pojazd() {
        this.cena = cena;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        this.nrRejString = nrRejString;
        this.x = x;
        this.y = y;
        this.rodzajPojazdu = rodzajPojazdu;
        wp = new Wrapper();
    }

    public abstract void parkowanie();

    public abstract void wyjazd();

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setNrRejString(String nrRejString) {
        this.nrRejString = nrRejString;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setRodzajPojazdu(Integer rodzajPojazdu) {
        this.rodzajPojazdu = rodzajPojazdu;
    }

    public void setDataIn(LocalDateTime dataIn) {
        this.dataIn = dataIn;
    }

    public void setDataOut(LocalDateTime dataOut) {
        this.dataOut = dataOut;
    }

}