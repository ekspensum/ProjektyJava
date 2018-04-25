import java.time.LocalDateTime;

public class Dziennik {

    private LocalDateTime dataWpisu, dataOceny;
    private String imieUcznia, nazwiskoUcznia;
    private Integer idUcznia;
    private Double ocena;
    private String imieNauczyciela, nazwiskoNauczyciela;
    private Integer idNauczyciela;
    private String uwagiNauczyciela;
    private String nazwaKlasy;
    private Integer idKlasy;
    private String nazwaPrzedmiotu;
    private Integer idPrzedmiotu;

    public void setDataWpisu(LocalDateTime dataWpisu) {
        this.dataWpisu = dataWpisu;
    }

    public void setDataOceny(LocalDateTime dataOceny) {
        this.dataOceny = dataOceny;
    }

    public void setImieUcznia(String imieUcznia) {
        this.imieUcznia = imieUcznia;
    }

    public void setNazwiskoUcznia(String nazwiskoUcznia) {
        this.nazwiskoUcznia = nazwiskoUcznia;
    }

    public void setIdUcznia(Integer idUcznia) {
        this.idUcznia = idUcznia;
    }

    public void setOcena(Double ocena) {
        this.ocena = ocena;
    }

    public void setImieNauczyciela(String imieNauczyciela) {
        this.imieNauczyciela = imieNauczyciela;
    }

    public void setNazwiskoNauczyciela(String nazwiskoNauczyciela) {
        this.nazwiskoNauczyciela = nazwiskoNauczyciela;
    }

    public void setIdNauczyciela(Integer idNauczyciela) {
        this.idNauczyciela = idNauczyciela;
    }

    public void setNazwaKlasy(String nazwaKlasy) {
        this.nazwaKlasy = nazwaKlasy;
    }

    public void setIdKlasy(Integer idKlasy) {
        this.idKlasy = idKlasy;
    }

    public void setNazwaPrzedmiotu(String nazwaPrzedmiotu) {
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
    }

    public void setIdPrzedmiotu(Integer idPrzedmiotu) {
        this.idPrzedmiotu = idPrzedmiotu;
    }

    public void setUwagiNauczyciela(String uwagiNauczyciela) {
        this.uwagiNauczyciela = uwagiNauczyciela;
    }

}
