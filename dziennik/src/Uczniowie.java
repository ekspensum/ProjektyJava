import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Uczniowie {
    private String imieUcznia, nazwiskoUcznia, loginUcznia, hasloUcznia;
    private Date dataUrodzenia;

    public Uczniowie(String imieUcznia, String nazwiskoUcznia, String loginUcznia, String hasloUcznia, Date dataUrodzenia) {
        this.imieUcznia = imieUcznia;
        this.nazwiskoUcznia = nazwiskoUcznia;
        this.loginUcznia = loginUcznia;
        this.hasloUcznia = hasloUcznia;
        this.dataUrodzenia = dataUrodzenia;
    }

    public String getImieUcznia() {
        return imieUcznia;
    }

    public String getNazwiskoUcznia() {
        return nazwiskoUcznia;
    }

    public String getLoginUcznia() {
        return loginUcznia;
    }

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }
}
