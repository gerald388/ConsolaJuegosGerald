package games.criptavoraz;

import java.util.HashSet;
import java.util.Set;

public class RayoMaldito {
    public Set<String> recorrer(PlanoDeLaCripta plano, int fila, int columna, int direccion) {
        Set<String> casillasIluminadas = new HashSet<String>();
        int[] filaPaso = {-1, 0, 1, 0};
        int[] columnaPaso = {0, 1, 0, -1};
        int actualFila = fila + filaPaso[direccion];
        int actualColumna = columna + columnaPaso[direccion];

        while (plano.esInterior(actualFila, actualColumna) && !plano.esPared(actualFila, actualColumna)) {
            casillasIluminadas.add(actualFila + ":" + actualColumna);
            plano.encenderCristalSiExiste(actualFila, actualColumna);
            char casilla = plano.getCasilla(actualFila, actualColumna);
            if (casilla == '/' || casilla == '\\') {
                direccion = desviar(direccion, casilla);
            }
            actualFila += filaPaso[direccion];
            actualColumna += columnaPaso[direccion];
        }
        return casillasIluminadas;
    }

    private int desviar(int direccion, char espejo) {
        if (espejo == '/') {
            int[] cambio = {1, 0, 3, 2};
            return cambio[direccion];
        }
        int[] cambio = {3, 2, 1, 0};
        return cambio[direccion];
    }
}
