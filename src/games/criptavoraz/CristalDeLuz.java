package games.criptavoraz;

public class CristalDeLuz {
    private final int fila;
    private final int columna;
    private boolean encendido;

    public CristalDeLuz(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public boolean estaEncendido() { return encendido; }
    public void encender() { encendido = true; }
}
