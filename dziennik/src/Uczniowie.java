import java.time.LocalDateTime;
import java.util.ArrayList;

public class Uczniowie {
    private String imieUcznia, nazwiskoUcznia, loginUcznia, hasloUcznia;
    private LocalDateTime dataUrodzenia;
    private ArrayList<Uczniowie> uczniowieWszyscy = new ArrayList<>();

    public String getImieUcznia() {
        return imieUcznia;
    }

    public void setImieUcznia(String imieUcznia) {
        this.imieUcznia = imieUcznia;
    }

    public String getNazwiskoUcznia() {
        return nazwiskoUcznia;
    }

    public void setNazwiskoUcznia(String nazwiskoUcznia) {
        this.nazwiskoUcznia = nazwiskoUcznia;
    }

    public String getLoginUcznia() {
        return loginUcznia;
    }

    public void setLoginUcznia(String loginUcznia) {
        this.loginUcznia = loginUcznia;
    }

    public String getHasloUcznia() {
        return hasloUcznia;
    }

    public void setHasloUcznia(String hasloUcznia) {
        this.hasloUcznia = hasloUcznia;
    }

    public LocalDateTime getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(LocalDateTime dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public ArrayList<Uczniowie> getUczniowieWszyscy() {
        return uczniowieWszyscy;
    }

    public void setUczniowieWszyscy(ArrayList<Uczniowie> uczniowieWszyscy) {
        this.uczniowieWszyscy = uczniowieWszyscy;
    }
}
