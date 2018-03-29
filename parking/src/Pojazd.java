import java.time.LocalDateTime;


public abstract class Pojazd {
    protected Double cena;
    protected LocalDateTime dataIn, dataOut;
    protected String nrRejString;
    protected Integer x, y;
    protected Integer rodzajPojazdu;
    protected Wrapper wp;

    public Pojazd(Double cena, LocalDateTime dataIn, LocalDateTime dataOut, String nrRejString, Integer x, Integer y, Integer rodzajPojazdu) {
        this.cena = cena;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        this.nrRejString = nrRejString;
        this.x = x;
        this.y = y;
        this.rodzajPojazdu = rodzajPojazdu;
        wp = new Wrapper();
    }

    public Pojazd(LocalDateTime dataOut) {
        this.dataOut = dataOut;
    }

    public abstract void parkowanie();

    public abstract void wyjazd(int index);

}