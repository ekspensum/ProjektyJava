package sample;

public class Kosztorysy {
    int lp;
    double ilosc, cena, wartosc;
    String norma, opis, jm;

    public Kosztorysy(int lp, String norma, String opis, String jm, double ilosc, double cena, double wartosc) {
        this.lp = lp;
        this.ilosc = ilosc;
        this.cena = cena;
        this.wartosc = wartosc;
        this.norma = norma;
        this.opis = opis;
        this.jm = jm;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public double getIlosc() {
        return ilosc;
    }

    public void setIlosc(double ilosc) {
        this.ilosc = ilosc;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getWartosc() {
        return wartosc;
    }

    public void setWartosc(double wartosc) {
        this.wartosc = wartosc;
    }

    public String getNorma() {
        return norma;
    }

    public void setNorma(String norma) {
        this.norma = norma;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getJm() {
        return jm;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }
}
