
package estadisticas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GestorEstadisticas {

    // Identificadores de nuestros juegos.
    public static final String PALABRA_OCULTA = "palabraoculta";
    public static final String DUELO_XO = "dueloxo";
    public static final String CRIPTA = "criptavoraz";

    private final Map<String, ArrayList<Registro>> registros = new HashMap<>();
    private final Path carpeta = Paths.get("records");

    // Constructor: cargar records o crear los ficticios.
    public GestorEstadisticas() {
        cargarOInicializar(PALABRA_OCULTA,
                new Registro("Daniel", 160),
                new Registro("Maria", 140),
                new Registro("Carlos", 120));

        cargarOInicializar(DUELO_XO,
                new Registro("Sofia", 30),
                new Registro("Andres", 21),
                new Registro("Luis", 15));

        cargarOInicializar(CRIPTA,
                new Registro("Valeria", 18),
                new Registro("Diego", 24),
                new Registro("Camila", 31));
    }

    // Cargar los datos o crear los records iniciales.
    private void cargarOInicializar(String juego, Registro... ejemplos) {
        ArrayList<Registro> lista = new ArrayList<>();
        Path archivo = carpeta.resolve("top_" + juego + ".txt");

        if (Files.exists(archivo)) {
            try (BufferedReader lector = Files.newBufferedReader(
                    archivo, StandardCharsets.UTF_8)) {

                String linea;

                while ((linea = lector.readLine()) != null) {
                    int separador = linea.lastIndexOf('|');

                    if (separador <= 0) continue;

                    try {
                        String nombre = linea.substring(0, separador).trim();
                        int puntos = Integer.parseInt(
                                linea.substring(separador + 1).trim());

                        if (!nombre.isEmpty() && puntos >= 0) {
                            lista.add(new Registro(nombre, puntos));
                        }

                    } catch (NumberFormatException error) {
                        System.out.println("Se ignoro un registro incorrecto de " + juego);
                    }
                }

            } catch (IOException error) {
                System.out.println("No se pudieron cargar los records de " + juego);
            }

        } else {
            lista.addAll(Arrays.asList(ejemplos));
        }

        registros.put(juego, lista);
        ordenar(juego);

        if (!Files.exists(archivo)) {
            guardar(juego);
        }
    }

    // Registrar la puntuacion conseguida por un jugador.
    public void registrarResultado(String juego, String nombre, int puntuacion) {

        if (!registros.containsKey(juego)) {
            System.out.println("Juego no reconocido para estadisticas.");
            return;
        }

        if (puntuacion < 0 || (juego.equals(CRIPTA) && puntuacion == 0)) {
            System.out.println("Puntuacion no valida.");
            return;
        }

        nombre = nombre == null ? "" : nombre.trim().replace('|', ' ')
                .replace('\n', ' ').replace('\r', ' ');

        if (nombre.isEmpty()) nombre = "Anonimo";

        ArrayList<Registro> lista = registros.get(juego);

        // Buscar si el jugador ya tiene una puntuacion.
        for (int i = 0; i < lista.size(); i++) {

            Registro anterior = lista.get(i);

            if (anterior.getNombreJugador().equalsIgnoreCase(nombre)) {

                if (juego.equals(DUELO_XO)) {
                    // Duelo X-O: acumular puntos.
                    puntuacion += anterior.getPuntuacion();

                } else if (juego.equals(CRIPTA)) {
                    // Cripta: conservar menos movimientos.
                    puntuacion = Math.min(puntuacion, anterior.getPuntuacion());

                } else {
                    // Palabra Oculta: conservar el mejor puntaje.
                    puntuacion = Math.max(puntuacion, anterior.getPuntuacion());
                }

                lista.set(i, new Registro(anterior.getNombreJugador(), puntuacion));
                ordenar(juego);
                guardar(juego);
                return;
            }
        }

        // Agregar jugador nuevo.
        lista.add(new Registro(nombre, puntuacion));
        ordenar(juego);
        guardar(juego);
    }

    // Mostrar los tres mejores resultados.
    public void mostrarTop3(String juego) {

        ArrayList<Registro> lista = registros.get(juego);

        if (lista == null) {
            System.out.println("Juego no reconocido.");
            return;
        }

        System.out.println("\n===== TOP 3: " + nombreJuego(juego) + " =====");

        if (lista.isEmpty()) {
            System.out.println("Todavia no hay records.");
            return;
        }

        for (int i = 0; i < Math.min(3, lista.size()); i++) {

            Registro r = lista.get(i);

            String unidad = juego.equals(CRIPTA)
                    ? " movimientos" : " puntos";

            System.out.println((i + 1) + ". " + r.getNombreJugador()
                    + " - " + r.getPuntuacion() + unidad);
        }
    }

    // Ordenar los records de mejor a peor.
    private void ordenar(String juego) {

        ArrayList<Registro> lista = registros.get(juego);

        lista.sort((a, b) -> {

            int comparacion = juego.equals(CRIPTA)
                    ? Integer.compare(a.getPuntuacion(), b.getPuntuacion())
                    : Integer.compare(b.getPuntuacion(), a.getPuntuacion());

            return comparacion != 0 ? comparacion
                    : a.getNombreJugador().compareToIgnoreCase(b.getNombreJugador());
        });
    }

    // Guardar los resultados en un archivo de texto.
    private void guardar(String juego) {

        try {
            Files.createDirectories(carpeta);

            Path archivo = carpeta.resolve("top_" + juego + ".txt");

            try (BufferedWriter escritor = Files.newBufferedWriter(
                    archivo, StandardCharsets.UTF_8)) {

                for (Registro r : registros.get(juego)) {
                    escritor.write(r.getNombreJugador() + "|" + r.getPuntuacion());
                    escritor.newLine();
                }
            }

        } catch (IOException error) {
            System.out.println("No se pudieron guardar los records de " + juego);
        }
    }

    // Obtener el nombre visible del juego.
    private String nombreJuego(String juego) {

        switch (juego) {
            case PALABRA_OCULTA:
                return "Palabra Oculta";

            case DUELO_XO:
                return "Duelo X-O";

            case CRIPTA:
                return "Cripta de los Espejos Voraces";

            default:
                return juego;
        }
    }
}
