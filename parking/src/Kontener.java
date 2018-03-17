import java.time.LocalDateTime;

public class Kontener {
    private double cena;
    private LocalDateTime dataIn, dataOut;
    private String nrRejString;
    private Integer x, y;
    private Integer rodzajPojazdu;

    public Kontener() {
        this.cena = cena;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
        this.nrRejString = nrRejString;
        this.x = x;
        this.y = y;
        this.rodzajPojazdu = rodzajPojazdu;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setDataIn(LocalDateTime dataIn) {
        this.dataIn = dataIn;
    }

    public void setDataOut(LocalDateTime dataOut) {
        this.dataOut = dataOut;
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

    public double getCena() {
        return cena;
    }

    public LocalDateTime getDataIn() {
        return dataIn;
    }

    public LocalDateTime getDataOut() {
        return dataOut;
    }

    public String getNrRejString() {
        return nrRejString;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getRodzajPojazdu() {
        return rodzajPojazdu;
    }
}
