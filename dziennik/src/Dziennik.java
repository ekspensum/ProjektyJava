import java.util.ArrayList;
import java.util.Date;

public class Dziennik<N, K> {

    private N nauczycieleObiekt;
    private K klasyObiekt;
    private Date dataWpisu, dataOceny;
    private Double ocena;
    private String uwagiNauczyciela;

    public Dziennik(Date dataWpisu, Date dataOceny, Double ocena, String uwagiNauczyciela) {
        this.dataWpisu = dataWpisu;
        this.dataOceny = dataOceny;
        this.ocena = ocena;
        this.uwagiNauczyciela = uwagiNauczyciela;
    }

    public Dziennik(N nauczycieleObiekt, K klasyObiekt) {
        this.nauczycieleObiekt = nauczycieleObiekt;
        this.klasyObiekt = klasyObiekt;
    }

    public Dziennik() {
    }

    public N getNauczycieleObiekt() {
        return nauczycieleObiekt;
    }

    public K getKlasyObiekt() {
        return klasyObiekt;
    }

    public Date getDataWpisu() {
        return dataWpisu;
    }

    public Date getDataOceny() {
        return dataOceny;
    }

    public Double getOcena() {
        return ocena;
    }

    public String getUwagiNauczyciela() {
        return uwagiNauczyciela;
    }

}
