
package consola;

import java.util.Scanner;
import games.Jugable;
import games.palabraoculta.PalabraOculta;
import games.dueloxo.DueloXO;
import games.criptavoraz.PortalDeObsidiana;
import estadisticas.GestorEstadisticas;

public class Main {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        GestorEstadisticas gestor = new GestorEstadisticas();

        boolean salir = false;

        while (!salir) {

            System.out.println("\n===== CONSOLA DE JUEGOS =====");
            System.out.println("1. Lanzar juego");
            System.out.println("2. Ver estadisticas");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

            String opcion = teclado.nextLine();

            switch (opcion) {

                case "1":
                    menuJuegos(teclado, gestor);
                    break;

                case "2":
                    menuEstadisticas(teclado, gestor);
                    break;

                case "3":
                    System.out.println("Gracias por jugar.");
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }

        teclado.close();
    }

    // Menu para lanzar los juegos.
    public static void menuJuegos(Scanner teclado, GestorEstadisticas gestor) {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== LANZAR JUEGO =====");
            System.out.println("1. Palabra Oculta");
            System.out.println("2. Duelo X-O");
            System.out.println("3. Cripta de los Espejos Voraces");
            System.out.println("4. Volver");
            System.out.print("Seleccione un juego: ");

            String opcion = teclado.nextLine();

            Jugable juego = null;

            switch (opcion) {

                case "1":
                    juego = new PalabraOculta(teclado, gestor);
                    break;

                case "2":
                    juego = new DueloXO(teclado, gestor);
                    break;

                case "3":
                    juego = new PortalDeObsidiana(teclado, gestor);
                    break;

                case "4":
                    volver = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

            if (juego != null) {
                juego.Start();
            }
        }
    }

    // Menu para consultar los records.
    public static void menuEstadisticas(
            Scanner teclado, GestorEstadisticas gestor) {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== VER ESTADISTICAS =====");
            System.out.println("1. Top 3 - Palabra Oculta");
            System.out.println("2. Top 3 - Duelo X-O");
            System.out.println("3. Top 3 - Cripta de los Espejos Voraces");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opcion: ");

            String opcion = teclado.nextLine();

            switch (opcion) {

                case "1":
                    gestor.mostrarTop3(GestorEstadisticas.PALABRA_OCULTA);
                    break;

                case "2":
                    gestor.mostrarTop3(GestorEstadisticas.DUELO_XO);
                    break;

                case "3":
                    gestor.mostrarTop3(GestorEstadisticas.CRIPTA);
                    break;

                case "4":
                    volver = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
}


