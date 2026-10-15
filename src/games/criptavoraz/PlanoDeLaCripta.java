package games.criptavoraz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlanoDeLaCripta {
    private final char[][] mapa;
    private final List<CristalDeLuz> cristales = new ArrayList<CristalDeLuz>();
    private final PuertaSepulcral salida;
    private final Set<String> rayos = new HashSet<String>();

    public PlanoDeLaCripta() {
        String[] diseño = {
            "#############",
            "#I.........S#",
            "#.#########.#",
            "#...C.......#",
            "#...........#",
            "#>..\\...#C.<#",
            "#############"
        };
        mapa = new char[diseño.length][diseño[0].length()];
        PuertaSepulcral puertaEncontrada = null;
        for (int fila = 0; fila < diseño.length; fila++) {
            for (int columna = 0; columna < diseño[fila].length(); columna++) {
                mapa[fila][columna] = diseño[fila].charAt(columna);
                if (mapa[fila][columna] == 'C') cristales.add(new CristalDeLuz(fila, columna));
                if (mapa[fila][columna] == 'S') puertaEncontrada = new PuertaSepulcral(fila, columna);
            }
        }
        salida = puertaEncontrada;
        recalcularRayos();
    }

    public void recalcularRayos() {
        rayos.clear();
        RayoMaldito calculador = new RayoMaldito();
        for (int fila = 0; fila < mapa.length; fila++) {
            for (int columna = 0; columna < mapa[fila].length; columna++) {
                if (mapa[fila][columna] == '>') rayos.addAll(calculador.recorrer(this, fila, columna, 1));
                if (mapa[fila][columna] == '<') rayos.addAll(calculador.recorrer(this, fila, columna, 3));
            }
        }
    }

    public boolean esInterior(int fila, int columna) { return fila >= 0 && fila < mapa.length && columna >= 0 && columna < mapa[0].length; }
    public boolean esPared(int fila, int columna) { return mapa[fila][columna] == '#'; }
    public char getCasilla(int fila, int columna) { return mapa[fila][columna]; }
    public boolean estaIluminada(int fila, int columna) { return rayos.contains(fila + ":" + columna); }
    public PuertaSepulcral getSalida() { return salida; }

    public void encenderCristalSiExiste(int fila, int columna) {
        for (CristalDeLuz cristal : cristales) {
            if (cristal.getFila() == fila && cristal.getColumna() == columna) cristal.encender();
        }
    }

    public boolean todosLosCristalesEncendidos() {
        for (CristalDeLuz cristal : cristales) if (!cristal.estaEncendido()) return false;
        return true;
    }

    public int cristalesEncendidos() {
        int total = 0;
        for (CristalDeLuz cristal : cristales) if (cristal.estaEncendido()) total++;
        return total;
    }

    public int totalCristales() { return cristales.size(); }

    public boolean girarEspejoCercano(int fila, int columna) {
        int[] cambiosFila = {-1, 1, 0, 0};
        int[] cambiosColumna = {0, 0, -1, 1};
        for (int i = 0; i < cambiosFila.length; i++) {
            int objetivoFila = fila + cambiosFila[i];
            int objetivoColumna = columna + cambiosColumna[i];
            if (esInterior(objetivoFila, objetivoColumna)) {
                char casilla = mapa[objetivoFila][objetivoColumna];
                if (casilla == '/' || casilla == '\\') {
                    EspejoHambriento espejo = new EspejoHambriento(casilla);
                    espejo.girar();
                    mapa[objetivoFila][objetivoColumna] = espejo.getOrientacion();
                    recalcularRayos();
                    return true;
                }
            }
        }
        return false;
    }

    public void dibujar(ExploradorPerdido jugador) {
        for (int fila = 0; fila < mapa.length; fila++) {
            for (int columna = 0; columna < mapa[fila].length; columna++) {
                if (fila == jugador.getFila() && columna == jugador.getColumna()) System.out.print('@');
                else if (mapa[fila][columna] == 'C') System.out.print(estaCristalEncendido(fila, columna) ? '*' : 'C');
                else if (mapa[fila][columna] == 'S' && !todosLosCristalesEncendidos()) System.out.print('X');
                else if (rayos.contains(fila + ":" + columna) && mapa[fila][columna] == '.') System.out.print('·');
                else System.out.print(mapa[fila][columna]);
            }
            System.out.println();
        }
    }

    private boolean estaCristalEncendido(int fila, int columna) {
        for (CristalDeLuz cristal : cristales) if (cristal.getFila() == fila && cristal.getColumna() == columna) return cristal.estaEncendido();
        return false;
    }
}
