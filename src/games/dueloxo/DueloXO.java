
package games.dueloxo;

import java.util.Scanner;
import games.Jugable;
import estadisticas.GestorEstadisticas;

public class DueloXO implements Jugable {

    private Scanner teclado;
    private GestorEstadisticas gestor;
    private char[] tablero;

    // Constructor
    public DueloXO(Scanner teclado, GestorEstadisticas gestor) {
        this.teclado = teclado;
        this.gestor = gestor;
    }

    // Metodo obligatorio de la interfaz Jugable
    @Override
    public void Start() {
        jugar();
    }

    public void jugar() {

        System.out.println("\n===== DUELO X-O =====");

        System.out.print("Nombre del jugador 1 (X): ");
        String jugador1 = teclado.nextLine().trim();

        System.out.print("Nombre del jugador 2 (O): ");
        String jugador2 = teclado.nextLine().trim();

        // Crear el tablero.
        tablero = new char[]{
                '1', '2', '3',
                '4', '5', '6',
                '7', '8', '9'
        };

        char simbolo = 'X';
        int turnos = 0;

        while (turnos < 9) {

            mostrarTablero();

            String jugadorActual;

            if (simbolo == 'X') {
                jugadorActual = jugador1;
            } else {
                jugadorActual = jugador2;
            }

            System.out.println("\nTurno de " + jugadorActual);
            System.out.print("Elige una posicion (1-9): ");

            String entrada = teclado.nextLine().trim();

            // Validar la posicion.
            if (entrada.length() != 1 ||
                    entrada.charAt(0) < '1' ||
                    entrada.charAt(0) > '9') {

                System.out.println("Posicion no valida.");
                continue;
            }

            int posicion = entrada.charAt(0) - '1';

            if (tablero[posicion] == 'X' ||
                    tablero[posicion] == 'O') {

                System.out.println("Esta posicion ya esta ocupada.");
                continue;
            }

            // Colocar el simbolo.
            tablero[posicion] = simbolo;
            turnos++;

            // Verificar si alguien gano.
            if (hayGanador(simbolo)) {

                mostrarTablero();

                System.out.println("\n¡Gana " + jugadorActual + "!");
                System.out.println("Puntuacion obtenida: 3 puntos");

                gestor.registrarResultado(
                        GestorEstadisticas.DUELO_XO,
                        jugadorActual,
                        3
                );

                return;
            }

            // Cambiar jugador.
            if (simbolo == 'X') {
                simbolo = 'O';
            } else {
                simbolo = 'X';
            }
        }

        // Si nadie gano, hay empate.
        mostrarTablero();

        System.out.println("\n¡La partida termino en empate!");
        System.out.println("Ambos jugadores reciben 1 punto.");

        gestor.registrarResultado(
                GestorEstadisticas.DUELO_XO,
                jugador1,
                1
        );

        gestor.registrarResultado(
                GestorEstadisticas.DUELO_XO,
                jugador2,
                1
        );
    }

    // Mostrar el tablero.
    private void mostrarTablero() {

        System.out.println();

        System.out.println(" " + tablero[0] + " | " + tablero[1] + " | " + tablero[2]);
        System.out.println("---+---+---");
        System.out.println(" " + tablero[3] + " | " + tablero[4] + " | " + tablero[5]);
        System.out.println("---+---+---");
        System.out.println(" " + tablero[6] + " | " + tablero[7] + " | " + tablero[8]);
    }

    // Comprobar si existe una linea ganadora.
    private boolean hayGanador(char s) {

        return
                (tablero[0] == s && tablero[1] == s && tablero[2] == s) ||
                        (tablero[3] == s && tablero[4] == s && tablero[5] == s) ||
                        (tablero[6] == s && tablero[7] == s && tablero[8] == s) ||
                        (tablero[0] == s && tablero[3] == s && tablero[6] == s) ||
                        (tablero[1] == s && tablero[4] == s && tablero[7] == s) ||
                        (tablero[2] == s && tablero[5] == s && tablero[8] == s) ||
                        (tablero[0] == s && tablero[4] == s && tablero[8] == s) ||
                        (tablero[2] == s && tablero[4] == s && tablero[6] == s);
    }
}
