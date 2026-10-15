package games.criptavoraz;

public class EspejoHambriento {
    private char orientacion;

    public EspejoHambriento(char orientacion) {
        this.orientacion = orientacion;
    }

    public char getOrientacion() { return orientacion; }

    public void girar() {
        orientacion = orientacion == '/' ? '\\' : '/';
    }
}
