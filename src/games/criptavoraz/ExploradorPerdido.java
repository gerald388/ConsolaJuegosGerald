package games.criptavoraz;

public class ExploradorPerdido {
    private int fila;
    private int columna;
    private int vidas = 3;
    private int movimientos;

    public ExploradorPerdido(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public int getVidas() { return vidas; }
    public int getMovimientos() { return movimientos; }

    public void moverA(int nuevaFila, int nuevaColumna) {
        fila = nuevaFila;
        columna = nuevaColumna;
        movimientos++;
    }

    public void registrarAccion() { movimientos++; }
    public void recibirQuemadura() { vidas--; }
}
