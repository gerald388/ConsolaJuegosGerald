
package estadisticas;

public class Registro {

    private String nombreJugador;
    private int puntuacion;

    public Registro(String nombreJugador, int puntuacion) {
        this.nombreJugador = nombreJugador;
        this.puntuacion = puntuacion;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
}

