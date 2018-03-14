public class Filtr {
    Character[] znaki = {'#'};
    boolean sp;

    public Filtr() {
        this.sp = true;
    }

    public boolean filtrZnakow(String tekst) {
        sp = true;
        if (tekst.length() == 0) sp = false;
        for (int i = 0; i < tekst.length(); i++)
            for (int j = 0; j < znaki.length; j++)
                if (tekst.toCharArray()[i] == znaki[j]) sp = false;
        return sp;
    }
}
