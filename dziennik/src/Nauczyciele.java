import java.time.LocalDateTime;
import java.util.Date;

public class Nauczyciele {
    private String imieNauczyciela, nazwiskoNauczyciela, loginNauczyciela, haslowNauczyciela;
    private LocalDateTime dataZatrudnienia;

    public String getImieNauczyciela() {
        return imieNauczyciela;
    }

    public void setImieNauczyciela(String imieNauczyciela) {
        this.imieNauczyciela = imieNauczyciela;
    }

    public String getNazwiskoNauczyciela() {
        return nazwiskoNauczyciela;
    }

    public void setNazwiskoNauczyciela(String nazwiskoNauczyciela) {
        this.nazwiskoNauczyciela = nazwiskoNauczyciela;
    }

    public String getLoginNauczyciela() {
        return loginNauczyciela;
    }

    public void setLoginNauczyciela(String loginNauczyciela) {
        this.loginNauczyciela = loginNauczyciela;
    }

    public String getHaslowNauczyciela() {
        return haslowNauczyciela;
    }

    public void setHaslowNauczyciela(String haslowNauczyciela) {
        this.haslowNauczyciela = haslowNauczyciela;
    }

    public LocalDateTime getDataZatrudnienia() {
        return dataZatrudnienia;
    }

    public void setDataZatrudnienia(LocalDateTime dataZatrudnienia) {
        this.dataZatrudnienia = dataZatrudnienia;
    }
}
