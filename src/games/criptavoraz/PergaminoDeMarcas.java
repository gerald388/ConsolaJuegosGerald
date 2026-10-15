package games.criptavoraz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PergaminoDeMarcas {
    private static final String RUTA = "records/record_cripta.txt";

    public void registrarSiMejora(String nombre, int movimientos) {
        String[] datos = cargarDatos();
        int anterior = Integer.parseInt(datos[1]);
        if (anterior == -1 || movimientos < anterior) {
            guardar(nombre, movimientos);
            System.out.println("¡Nueva marca de la Cripta!");
        }
    }

    public String leerMarca() {
        String[] datos = cargarDatos();
        if (datos[1].equals("-1")) return "Cripta: aun no hay una expedicion victoriosa.";
        return "Cripta: " + datos[0] + " escapo en " + datos[1] + " movimientos.";
    }

    private String[] cargarDatos() {
        File archivo = new File(RUTA);
        if (!archivo.exists()) return new String[] {"Nadie", "-1"};
        try {
            Scanner lector = new Scanner(archivo);
            String linea = lector.hasNextLine() ? lector.nextLine() : "Nadie|-1";
            lector.close();
            return linea.split("\\|", 2);
        } catch (IOException error) {
            return new String[] {"Nadie", "-1"};
        }
    }

    private void guardar(String nombre, int movimientos) {
        File carpeta = new File("records");
        carpeta.mkdirs();
        try {
            FileWriter escritor = new FileWriter(RUTA);
            escritor.write(nombre + "|" + movimientos);
            escritor.close();
        } catch (IOException error) {
            System.out.println("No se pudo guardar la marca de la Cripta.");
        }
    }
}
