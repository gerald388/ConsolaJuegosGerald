
package games.criptavoraz;

import java.util.Scanner;
import games.Jugable;
import estadisticas.GestorEstadisticas;

public class PortalDeObsidiana implements Jugable {

    private final Scanner teclado;
    private final GestorEstadisticas gestor;

    // Constructor
    public PortalDeObsidiana(Scanner teclado, GestorEstadisticas gestor) {
        this.teclado = teclado;
        this.gestor = gestor;
    }

    // Metodo de la interfaz Jugable
    @Override
    public void Start() {
        mostrarReglas();
        comenzarExpedicion();
    }

    public void comenzarExpedicion() {

        System.out.print("\nNombre del explorador: ");
        String nombre = teclado.nextLine().trim();

        if (nombre.isEmpty()) {
            nombre = "Explorador anonimo";
        }

        PlanoDeLaCripta plano = new PlanoDeLaCripta();
        ExploradorPerdido jugador = new ExploradorPerdido(1, 1);

        boolean jugando = true;

        while (jugando && jugador.getVidas() > 0) {

            System.out.println("\n╔══ CRIPTA DE LOS ESPEJOS VORACES ══╗");
            System.out.println("║ Vidas: " + jugador.getVidas()
                    + " | Movimientos: " + jugador.getMovimientos()
                    + " | Cristales: " + plano.cristalesEncendidos()
                    + "/" + plano.totalCristales());
            System.out.println("╚═════════════════════════════════════╝");

            plano.dibujar(jugador);

            System.out.print("Accion [WASD mover, G girar, Q salir]: ");
            String entrada = teclado.nextLine().trim().toUpperCase();

            if (entrada.equals("Q")) {
                return;
            }

            if (entrada.equals("G")) {

                if (plano.girarEspejoCercano(
                        jugador.getFila(), jugador.getColumna())) {

                    jugador.registrarAccion();
                    System.out.println("El espejo cambio de hambre y orientacion.");

                } else {
                    System.out.println("No hay un espejo junto a ti.");
                    continue;
                }

            } else if (entrada.matches("[WASD]")) {

                intentarMover(entrada.charAt(0), jugador, plano);

            } else {
                System.out.println("Accion no reconocida.");
                continue;
            }

            if (plano.estaIluminada(
                    jugador.getFila(), jugador.getColumna())) {

                jugador.recibirQuemadura();
                System.out.println("¡Un rayo maldito te alcanzo! Pierdes una vida.");
            }

            PuertaSepulcral salida = plano.getSalida();

            // Comprobar si el jugador logro escapar.
            if (jugador.getFila() == salida.getFila()
                    && jugador.getColumna() == salida.getColumna()
                    && plano.todosLosCristalesEncendidos()) {

                System.out.println("\n¡Escapaste de la Cripta en "
                        + jugador.getMovimientos() + " movimientos!");

                // Registrar resultado en el Top 3.
                gestor.registrarResultado(
                        GestorEstadisticas.CRIPTA,
                        nombre,
                        jugador.getMovimientos()
                );

                jugando = false;
            }
        }

        if (jugador.getVidas() == 0) {
            System.out.println(
                    "La Cripta devoro tus ultimas tres oportunidades.");
        }
    }

    // Movimiento del explorador
    private void intentarMover(
            char direccion,
            ExploradorPerdido jugador,
            PlanoDeLaCripta plano) {

        int fila = jugador.getFila();
        int columna = jugador.getColumna();

        if (direccion == 'W') fila--;
        if (direccion == 'S') fila++;
        if (direccion == 'A') columna--;
        if (direccion == 'D') columna++;

        if (!plano.esInterior(fila, columna)
                || plano.esPared(fila, columna)
                || plano.getCasilla(fila, columna) == '>'
                || plano.getCasilla(fila, columna) == '<') {

            System.out.println("Ese camino esta cerrado.");
            return;
        }

        if (plano.getCasilla(fila, columna) == 'S'
                && !plano.todosLosCristalesEncendidos()) {

            System.out.println(
                    "La puerta sigue sellada: faltan cristales por encender.");
            return;
        }

        jugador.moverA(fila, columna);
    }

    // Mostrar las instrucciones
    public void mostrarReglas() {

        System.out.println("\nCRIPTA DE LOS ESPEJOS VORACES");

        System.out.println(
                "Llega a S despues de encender todos los cristales C con los rayos.");

        System.out.println(
                "Usa WASD para caminar. G gira un espejo / o \\ situado junto a ti.");

        System.out.println(
                "Las casillas iluminadas reciben luz: si acabas el turno en una, pierdes una vida.");

        System.out.println(
                "Tienes tres vidas. El record es el menor numero de movimientos victoriosos.");
    }
}
