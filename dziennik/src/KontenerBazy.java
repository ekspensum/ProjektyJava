import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class KontenerBazy implements Serializable {
    private Integer idKlasy;
    private String nazwaKlasy;
    private Integer idNauczyciela;
    private String imieNauczyciela, nazwiskoNauczyciela;
    private Integer idUcznia;
    private String imieUcznia, nazwiskoUcznia;
//    private Integer idPrzedmiotu; // ew. przyszłe zastosowanie
    private String nazwaPrzedmiotu;
    private LocalDateTime dataOperacji;
    private Date dataOceny;
    private Double ocena;
    private String komentarzaNauczyciela;

    public KontenerBazy(Integer idKlasy, String nazwaKlasy, Integer idNauczyciela, String imieNauczyciela, String nazwiskoNauczyciela, Integer idUcznia, String imieUcznia, String nazwiskoUcznia, String nazwaPrzedmiotu, LocalDateTime dataOperacji, Date dataOceny, Double ocena, String komentarzaNauczyciela) {
        this.idKlasy = idKlasy;
        this.nazwaKlasy = nazwaKlasy;
        this.idNauczyciela = idNauczyciela;
        this.imieNauczyciela = imieNauczyciela;
        this.nazwiskoNauczyciela = nazwiskoNauczyciela;
        this.idUcznia = idUcznia;
        this.imieUcznia = imieUcznia;
        this.nazwiskoUcznia = nazwiskoUcznia;
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
        this.dataOperacji = dataOperacji;
        this.dataOceny = dataOceny;
        this.ocena = ocena;
        this.komentarzaNauczyciela = komentarzaNauczyciela;
    }

    public Integer getIdKlasy() {
        return idKlasy;
    }

    public String getNazwaKlasy() {
        return nazwaKlasy;
    }

    public Integer getIdNauczyciela() {
        return idNauczyciela;
    }

    public String getImieNauczyciela() {
        return imieNauczyciela;
    }

    public String getNazwiskoNauczyciela() {
        return nazwiskoNauczyciela;
    }

    public Integer getIdUcznia() {
        return idUcznia;
    }

    public String getImieUcznia() {
        return imieUcznia;
    }

    public String getNazwiskoUcznia() {
        return nazwiskoUcznia;
    }

    public String getNazwaPrzedmiotu() {
        return nazwaPrzedmiotu;
    }

    public LocalDateTime getDataOperacji() {
        return dataOperacji;
    }

    public Date getDataOceny() {
        return dataOceny;
    }

    public Double getOcena() {
        return ocena;
    }

    public String getKomentarzaNauczyciela() {
        return komentarzaNauczyciela;
    }
}
