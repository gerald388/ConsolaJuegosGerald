
package games.palabraoculta;

import java.util.Scanner;
import games.Jugable;
import estadisticas.GestorEstadisticas;

public class PalabraOculta implements Jugable {

    private Scanner teclado;
    private GestorEstadisticas gestor;
    private String palabraSecreta;
    private char[] progreso;
    private int intentos;

    // Constructor
    public PalabraOculta(Scanner teclado, GestorEstadisticas gestor) {
        this.teclado = teclado;
        this.gestor = gestor;
    }

    // Metodo obligatorio de la interfaz Jugable
    @Override
    public void Start() {
        jugar();
    }

    public void jugar() {

        System.out.println("\n===== PALABRA OCULTA =====");

        System.out.print("Nombre del jugador 1: ");
        String jugador1 = teclado.nextLine().trim();

        System.out.print("Nombre del jugador 2: ");
        String jugador2 = teclado.nextLine().trim();

        // Jugador 1 elige una palabra.
        do {
            System.out.print(jugador1 + ", escribe la palabra secreta: ");
            palabraSecreta = teclado.nextLine().trim().toUpperCase();

            if (!palabraValida()) {
                System.out.println("Debes escribir una palabra que contenga solo letras.");
            }

        } while (!palabraValida());

        // Ocultar la palabra en la consola.
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }

        progreso = new char[palabraSecreta.length()];

        for (int i = 0; i < progreso.length; i++) {
            progreso[i] = '_';
        }

        intentos = 6;
        String letrasUsadas = "";

        // Iniciar los turnos.
        while (intentos > 0 && !palabraCompleta()) {

            System.out.println("\nTurno de " + jugador2);
            mostrarProgreso();

            System.out.println("Intentos restantes: " + intentos);
            System.out.println("Letras utilizadas: " + letrasUsadas);
            System.out.print("Introduce una letra: ");

            String entrada = teclado.nextLine().trim().toUpperCase();

            if (entrada.length() != 1 ||
                    !Character.isLetter(entrada.charAt(0))) {

                System.out.println("Debes escribir una sola letra.");
                continue;
            }

            char letra = entrada.charAt(0);

            if (letrasUsadas.indexOf(letra) != -1) {
                System.out.println("Ya utilizaste esa letra.");
                continue;
            }

            letrasUsadas += letra + " ";

            boolean acierto = false;

            // Revisar si la letra esta en la palabra.
            for (int i = 0; i < palabraSecreta.length(); i++) {

                if (palabraSecreta.charAt(i) == letra) {
                    progreso[i] = letra;
                    acierto = true;
                }
            }

            if (acierto) {
                System.out.println("¡Correcto!");
            } else {
                intentos--;
                System.out.println("Letra incorrecta.");
            }
        }

        // Mostrar resultado y registrar puntuacion.
        if (palabraCompleta()) {

            System.out.println("\n¡Ganaste, " + jugador2 + "!");

            // 100 puntos por ganar y 10 por intento restante.
            int puntos = 100 + (intentos * 10);

            System.out.println("Puntuacion obtenida: " + puntos);

            gestor.registrarResultado(
                    GestorEstadisticas.PALABRA_OCULTA,
                    jugador2,
                    puntos
            );

        } else {

            System.out.println("\nSe acabaron los intentos.");
            System.out.println("¡Gana " + jugador1 + "!");

            // 100 puntos por conseguir que no adivinen la palabra.
            int puntos = 100;

            System.out.println("Puntuacion obtenida: " + puntos);

            gestor.registrarResultado(
                    GestorEstadisticas.PALABRA_OCULTA,
                    jugador1,
                    puntos
            );
        }

        System.out.println("La palabra era: " + palabraSecreta);
    }

    // Verificar que la palabra contiene solamente letras.
    private boolean palabraValida() {

        if (palabraSecreta.isEmpty()) {
            return false;
        }

        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (!Character.isLetter(palabraSecreta.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // Mostrar el progreso de la palabra.
    private void mostrarProgreso() {

        System.out.print("Palabra: ");

        for (int i = 0; i < progreso.length; i++) {
            System.out.print(progreso[i] + " ");
        }

        System.out.println();
    }

    // Verificar si se adivino toda la palabra.
    private boolean palabraCompleta() {

        for (int i = 0; i < progreso.length; i++) {

            if (progreso[i] == '_') {
                return false;
            }
        }

        return true;
    }
}
